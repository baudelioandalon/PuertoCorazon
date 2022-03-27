package com.boreal.puertocorazon.ui

import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.boreal.commonutils.base.CUBaseActivity
import com.boreal.commonutils.component.dialogs.blurdialog.CUBlurDialog
import com.boreal.commonutils.extensions.setOnSingleClickListener
import com.boreal.puertocorazon.R
import com.boreal.puertocorazon.core.viewmodel.PCBaseViewModel
import com.boreal.puertocorazon.databinding.PcBaseActivityBinding
import com.boreal.puertocorazon.databinding.PcOutDialogBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.system.exitProcess

class PCBaseActivity : CUBaseActivity<PcBaseActivityBinding>() {

    private val viewModel: PCBaseViewModel by viewModel()
    lateinit var navController: NavController
    lateinit var dialog: CUBlurDialog

    override fun getLayout() = R.layout.pc_base_activity

    override fun initView() {
        initElements()
    }

    override fun onBackPressed() {
        if (viewModel.allowExit) {
            showOutDialog()
        } else {
            if (navController.currentDestination?.id == R.id.PCClientHomeFragment || navController.currentDestination?.id == R.id.PCAdmHomeFragment) {
                showOutDialog()
            } else {
                onBackPressed()
            }
        }
    }


    private fun showOutDialog() {
        lifecycleScope.launch {
            dialog = CUBlurDialog(
                resource = R.layout.pc_out_dialog,
                callback = {
                    PcOutDialogBinding.bind(it).apply {
                        btnCancelOut.setOnSingleClickListener {
                            viewModel.countOutClicked = 0
                            dialog.dismiss()
                        }
                        btnOut.setOnSingleClickListener {
                            exitProcess(0)
                        }
                    }
                },
                cancelable = true
            )
            dialog.showCustom(supportFragmentManager, "Dialogout")
        }
    }


}