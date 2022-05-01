package com.boreal.puertocorazon.ui

import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.boreal.commonutils.base.CUBaseActivity
import com.boreal.commonutils.component.dialogs.blurdialog.CUBlurDialog
import com.boreal.commonutils.extensions.onClick
import com.boreal.puertocorazon.R
import com.boreal.puertocorazon.core.viewmodel.PCMainViewModel
import com.boreal.puertocorazon.databinding.PcBaseActivityBinding
import com.boreal.puertocorazon.databinding.PcOutDialogBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.system.exitProcess

class PCBaseActivity : CUBaseActivity<PcBaseActivityBinding>() {

    private val mainViewModel: PCMainViewModel by viewModel()
    lateinit var navController: NavController
    lateinit var dialog: CUBlurDialog

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

    override fun initView() {
        initElements()
    }

    override fun onBackPressed() {
        if (mainViewModel.allowExit) {
            showOutDialog()
        } else {
            navController.currentDestination?.apply {
                if (id == R.id.PCClientHomeFragment ||
                    id == R.id.PCAdmHomeFragment ) {
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
        FirebaseAuth.getInstance().signOut()
        navController.popBackStack()
    }

}