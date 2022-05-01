package com.boreal.puertocorazon.addevent.ui.packages.addprice

import com.boreal.commonutils.extensions.onClick
import com.boreal.puertocorazon.addevent.R

fun PCAddPriceTicket.initElements() {
    binding.apply {

        txtTitle.text = getString(R.string.title_price_ticket_holder, title)

        btnAddPrice.onClick {
            priceTicket.invoke(txtPriceTicket.getIntegers())
            closeFragment()
        }

        btnBack.onClick {
            closeFragment()
        }
    }
}