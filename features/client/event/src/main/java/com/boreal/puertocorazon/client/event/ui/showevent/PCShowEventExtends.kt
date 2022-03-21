package com.boreal.puertocorazon.client.event.ui.showevent

import com.boreal.commonutils.extensions.setOnSingleClickListener

fun PCShowEventFragment.initElements() {

    mBinding.apply {
        btnClose.setOnSingleClickListener {
            requireActivity().onBackPressed()
        }
    }

}