package com.boreal.puertocorazon.addevent.ui.packages

import com.boreal.commonutils.extensions.setOnSingleClickListener
import com.boreal.puertocorazon.addevent.R

fun PCAddPriceTicket.initElements() {
    binding.apply {

        txtTitle.text = getString(R.string.title_price_ticket_holder, title)

        btnAddPrice.setOnSingleClickListener {
            priceTicket.invoke(txtPriceTicket.getIntegers())
            closeFragment()
        }

        btnBack.setOnSingleClickListener {
            closeFragment()
        }
    }
}