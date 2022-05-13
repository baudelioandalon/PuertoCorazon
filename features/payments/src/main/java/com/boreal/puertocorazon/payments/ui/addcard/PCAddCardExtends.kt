package com.boreal.puertocorazon.payments.ui.addcard

import com.boreal.commonutils.extensions.onClick

fun PCAddCardFragment.initElements() {
    binding.apply {
        btnBack.onClick {
            onFragmentBackPressed()
        }
    }
}