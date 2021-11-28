package com.boreal.puertocorazon

import com.boreal.commonutils.component.showToast

fun TestActivity.initElements(){
    mBinding.btnToast.setOnClickListener {
        showToast(viewModel.nombre)
    }
}