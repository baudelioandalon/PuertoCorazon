package com.boreal.puertocorazon.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.boreal.commonutils.base.CUBaseActivity
import com.boreal.commonutils.component.dialogs.blurdialog.CUBlurDialog
import com.boreal.commonutils.extensions.onClick
import com.boreal.puertocorazon.R
import com.boreal.puertocorazon.core.databinding.SplashBinding
import com.boreal.puertocorazon.core.utils.DialogGenericFragment
import com.boreal.puertocorazon.core.viewmodel.PCMainViewModel
import com.boreal.puertocorazon.databinding.PcBaseActivityBinding
import com.boreal.puertocorazon.databinding.PcOutDialogBinding
import com.google.firebase.auth.FirebaseAuth
import com.mercadopago.android.px.core.MercadoPagoCheckout
import com.mercadopago.android.px.model.Payment
import com.mercadopago.android.px.model.exceptions.MercadoPagoError
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.system.exitProcess

class PCBaseActivity : CUBaseActivity<PcBaseActivityBinding>() {

    val mainViewModel: PCMainViewModel by viewModel()
    lateinit var navController: NavController
    lateinit var dialog: CUBlurDialog
    lateinit var splashDialog: DialogGenericFragment<SplashBinding>

    override fun getLayout() = R.layout.pc_base_activity

    override fun initObservers() {
        mainViewModel.goLogin.observe(this) {
            it?.let {
                if (it) {
                    goToLogin()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        screenSplash.setKeepOnScreenCondition{false}
    }

    override fun initView() {
        initElements()
        mainViewModel.goToHomeClient = {
            navController.popBackStack(R.id.pc_client_menu_graph, false)
            navController.popBackStack(R.id.pc_adm_menu_graph, false)
        }
        mainViewModel.splash = { showSplash ->
            if (this::splashDialog.isInitialized) {
                if (showSplash && !splashDialog.isVisible && !splashDialog.isAdded) {
                    mainViewModel.resetLogin = true
                    splashDialog.show(supportFragmentManager, "dialog_splash")
                } else {
                    splashDialog.dismissAllowingStateLoss()
                }
            }
        }
    }

    override fun onBackPressed() {
        if (mainViewModel.allowExit) {
            showOutDialog()
        } else {
            navController.currentDestination?.apply {
                if (id == R.id.PCClientMenuFragment ||
                    id == R.id.PCAdmMenuFragment
                ) {
                    showOutDialog()
                } else {
                    super.onBackPressed()
                }
            }

        }
    }

    private fun showOutDialog() {
        lifecycleScope.launch {
            dialog = CUBlurDialog(
                resource = R.layout.pc_out_dialog,
                callback = {
                    PcOutDialogBinding.bind(it).apply {
                        btnCancelOut.onClick {
                            mainViewModel.countOutClicked = 0
                            dialog.dismiss()
                        }
                        btnOut.onClick {
                            exitProcess(0)
                        }
                    }
                },
                cancelable = true
            )
            dialog.showCustom(supportFragmentManager, "Dialogout")
        }
    }

    private fun goToLogin() {
        mainViewModel.logOut = true
        FirebaseAuth.getInstance().signOut()
        navController.popBackStack()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK && data != null || resultCode == MercadoPagoCheckout.PAYMENT_RESULT_CODE && data != null) {
                if (resultCode == MercadoPagoCheckout.PAYMENT_RESULT_CODE) {
                    val payment =
                        data.getSerializableExtra(MercadoPagoCheckout.EXTRA_PAYMENT_RESULT) as Payment?
                    if (payment == null) {
                        mainViewModel.resultCheckout(
                            canceled = false,
                            MercadoPagoError.createNotRecoverable(
                                "El pago no fue realizado",
                                "failed"
                            ), success = false
                        )
                        return
                    }
                    when (payment.paymentStatus) {
                        "rejected" -> {
                            mainViewModel.resultCheckout(
                                canceled = false,
                                MercadoPagoError(
                                    "El pago no fue realizado, por favor,\n pruebe otro medio de pago",
                                    "rejected",
                                    true
                                ), success = false
                            )
                        }
                        "approved" -> {
                            mainViewModel.resultCheckout(
                                canceled = false,
                                MercadoPagoError(
                                    "Pago exitoso, en unos momentos podrá ver sus tickets en su cuenta",
                                    payment.paymentStatus,
                                    false
                                ),
                                success = true
                            )
                        }
                        "pending" -> {
                            mainViewModel.resultCheckout(
                                canceled = false,
                                MercadoPagoError(
                                    "Realiza el pago con la referencia anterior",
                                    payment.paymentStatus,
                                    false
                                ),
                                success = true
                            )
                        }
                        else -> {
                            mainViewModel.resultCheckout(
                                canceled = false,
                                MercadoPagoError(
                                    "El pago no fue realizado",
                                    payment.paymentStatus,
                                    true
                                ),
                                success = false
                            )
                        }
                    }

                } else {

                }

            } else {
                if (resultCode == RESULT_CANCELED) {
                    data?.let {
                        if (data.extras != null && data.extras!!.containsKey(MercadoPagoCheckout.EXTRA_ERROR)
                        ) {
                            val mercadoPagoError =
                                data.getSerializableExtra(MercadoPagoCheckout.EXTRA_ERROR) as MercadoPagoError?
                            mainViewModel.resultCheckout(
                                canceled = true,
                                mercadoPagoError = mercadoPagoError ?: MercadoPagoError(
                                    "El pago no fue realizado",
                                    "canceled",
                                    false
                                ), success = false
                            )
                        } else {
                            mainViewModel.resultCheckout(
                                canceled = true,
                                mercadoPagoError = MercadoPagoError(
                                    "El pago no fue realizado",
                                    "canceled",
                                    false
                                ), success = false
                            )
                        }
                    }
                    mainViewModel.resultCheckout(
                        canceled = true,
                        mercadoPagoError = MercadoPagoError(
                            "El pago no fue realizado",
                            "canceled",
                            false
                        ), success = false
                    )
                } else {
                    mainViewModel.resultCheckout(
                        canceled = true,
                        mercadoPagoError = MercadoPagoError(
                            "El pago no fue realizado",
                            "failed",
                            false
                        ), success = false
                    )
                }
            }
        }
    }

}