package com.boreal.puertocorazon.showevent.ui.sedescription

import com.boreal.commonutils.extensions.onClick
import com.boreal.commonutils.extensions.openFacebookActivity
import com.boreal.commonutils.extensions.openMapsActivity
import com.boreal.commonutils.extensions.showToast
import com.boreal.puertocorazon.core.domain.entity.event.getAddressLatLng
import com.boreal.puertocorazon.core.domain.entity.event.getCity
import com.boreal.puertocorazon.core.domain.entity.event.getDateEvent
import com.boreal.puertocorazon.core.domain.entity.event.getHourEvent
import com.boreal.puertocorazon.core.domain.entity.shopping.PCShoppingModel
import com.boreal.puertocorazon.core.utils.formatCurrency
import com.boreal.puertocorazon.showevent.R


fun PCShowEventDescriptionFragment.initElements() {
    binding.apply {
        btnBuyTicketAdult.onClick {
            mainViewModel.getEventSelected().apply {
                mainViewModel.addShopping(
                    PCShoppingModel(
                        idEvent = idEvent,
                        idPackage = idEvent,
                        imageEvent = homeImageUrl,
                        titleEvent = title,
                        countAdult = 1,
                        priceElement = priceAdult.toFloat().toInt()
                    )
                )
            }
            showToast("Boleto agregado al carrito")
        }

        btnBuyTicketChild.onClick {
            mainViewModel.getEventSelected().apply {
                if (priceChild != 0L) {
                    mainViewModel.addShopping(
                        PCShoppingModel(
                            idEvent = idEvent,
                            idPackage = idEvent + "s",
                            imageEvent = homeImageUrl,
                            titleEvent = title,
                            countChild = 1,
                            priceElement = priceChild.toFloat().toInt()
                        )
                    )
                    showToast("Boleto agregado al carrito")
                } else {
                    btnBuyTicketAdult.performClick()
                }
            }
        }
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
            tvTicketPriceChild.text = if (priceChild != 0L) {
                priceChild.formatCurrency(textFirst = "Boleto \n Infantil")
            } else {
                getString(R.string.agregar_al_carrito_text)
            }
            tvTicketPriceAdult.text = priceAdult.formatCurrency(textFirst = "Boleto \n Adulto")
            txtCountPeople.text = "4" //TODO getTickets from firebase
            txtCity.text = getCity()
            txtHourEvent.text = getHourEvent()
            txtDateEvent.text = getDateEvent()
            btnFollow.onClick {
                try {
                    openFacebookActivity(
                        "739092302865258"
                    )
                } catch (e: Exception) {
                    showToast("No se pudo abrir el enlace")
                }

            }
            btnGoMaps.onClick {
                openMapsActivity(byLocation = getAddressLatLng())
            }
        }
    }
}