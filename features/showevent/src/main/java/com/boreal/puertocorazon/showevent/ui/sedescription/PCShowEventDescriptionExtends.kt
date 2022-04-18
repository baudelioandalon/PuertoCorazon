package com.boreal.puertocorazon.showevent.ui.sedescription

import com.boreal.commonutils.extensions.openFacebookActivity
import com.boreal.commonutils.extensions.setOnSingleClickListener
import com.boreal.puertocorazon.core.utils.formatCurrency


fun PCShowEventDescriptionFragment.initElements() {
    binding.apply {

    }
    fillData()
}

fun PCShowEventDescriptionFragment.fillData() {
    binding.apply {
        mainViewModel.getEventSelected().apply {
            imgUser = instructorImageUrl
            txtDescription.text = description
            txtAddressPlace.text = addressPlace
            tvInstructorName.text = instructorName
            tvTicketPrice.text = priceAdult.formatCurrency(textFirst = "Comprar")
            txtCountPeople.text = "4" //TODO getTickets from firebase
            btnFollow.setOnSingleClickListener {
                openFacebookActivity(
                    "739092302865258"
                )
            }
            btnGoMaps.setOnSingleClickListener {

            }
        }
    }
}