package com.boreal.puertocorazon.client.event.ui.showevent

import com.boreal.commonutils.extensions.setOnSingleClickListener

fun PCShowEventFragment.initElements() {

    binding.apply {
        btnClose.setOnSingleClickListener {
            requireActivity().onBackPressed()
        }
    }

}