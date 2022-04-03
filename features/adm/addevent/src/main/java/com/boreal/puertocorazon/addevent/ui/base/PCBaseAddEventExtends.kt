package com.boreal.puertocorazon.addevent.ui.base

import com.boreal.commonutils.extensions.setOnSingleClickListener

fun PCBaseAddEventFragment.initElements() {
    binding.apply {
        btnBack.setOnSingleClickListener {
            onFragmentBackPressed()
        }
    }
}