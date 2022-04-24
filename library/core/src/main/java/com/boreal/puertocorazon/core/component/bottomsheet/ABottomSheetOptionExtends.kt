package com.boreal.puertocorazon.core.component.bottomsheet

import com.boreal.commonutils.extensions.setOnSingleClickListener

fun ABottomSheetOptionsImageFragment.initElements() {
    mBinding.apply {
        roundableGalery.setOnSingleClickListener { getPermissionsStorage() }
        roundableCamera.setOnSingleClickListener { getPermissionsCamera() }
    }
}