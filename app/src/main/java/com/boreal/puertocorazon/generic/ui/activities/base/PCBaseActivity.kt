package com.boreal.puertocorazon.generic.ui.activities.base

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.boreal.commonutils.base.CUBaseActivity
import com.boreal.commonutils.dialogs.blurdialog.CUBlurDialog
import com.boreal.commonutils.extensions.setOnSingleClickListener
import com.boreal.puertocorazon.R
import com.boreal.puertocorazon.databinding.PcBaseActivityBinding
import com.boreal.puertocorazon.databinding.PcOutDialogBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PCBaseActivity : CUBaseActivity<PcBaseActivityBinding, PCBaseViewModel>(PCBaseViewModel::class) {

    lateinit var dialog: CUBlurDialog

    override fun getLayout() = R.layout.pc_base_activity

    override fun initDependency() {}

    override fun initObservers() {}

    override fun initView() {
        initElements()
    }

//    override fun onBackPressed() {
//        Log.e("backPressed", "Deshabilitado")
//        lifecycleScope.launch {
//            dialog = CUBlurDialog(resource = R.layout.pc_out_dialog, callback = {
//                PcOutDialogBinding.bind(it).apply {
//                    btnCancelOut.setOnSingleClickListener {
//                        viewModel.countOutClicked = 0
//                        dialog.dismiss()
//                    }
//                    btnOut.setOnSingleClickListener {
//                        finish()
//                    }
//                }
//            }, cancelable = true)
//            delay(300)
//            dialog.showCustom(this@PCBaseActivity.supportFragmentManager, "Dialogout")
//        }
//
//    }
}