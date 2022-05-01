package com.boreal.puertocorazon.core.component.bottomsheet

import com.boreal.commonutils.extensions.onClick

fun ABottomSheetOptionsImageFragment.initElements() {
    mBinding.apply {
        roundableGalery.onClick { getPermissionsStorage() }
        roundableCamera.onClick { getPermissionsCamera() }
    }
}