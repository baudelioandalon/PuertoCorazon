package com.boreal.puertocorazon.ticket.ui.showqr

import com.boreal.commonutils.extensions.onClick

fun PCShowQrTickets.initElements() {
    binding.apply {
        btnBack.onClick {
            closeFragment()
        }
    }
}