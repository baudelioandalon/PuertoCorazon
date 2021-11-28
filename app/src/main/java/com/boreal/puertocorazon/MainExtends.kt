package com.boreal.puertocorazon

import com.boreal.commonutils.component.showToast

fun MainActivity.initElements() {
    mBinding.btnToast.setOnClickListener {
        showToast(viewModel.nombre)
    }
}

