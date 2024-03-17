package com.boreal.puertocorazon.ticket.ui.showqr

import android.content.Intent
import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieDrawable
import com.boreal.commonutils.extensions.changeDrawable
import com.boreal.commonutils.extensions.changeTextColor
import com.boreal.commonutils.extensions.hideView
import com.boreal.commonutils.extensions.itemPercent
import com.boreal.commonutils.extensions.onClick
import com.boreal.commonutils.extensions.openMapsActivity
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.core.domain.entity.event.getAddressLatLng
import com.boreal.puertocorazon.core.domain.entity.event.getCity
import com.boreal.puertocorazon.core.domain.entity.event.getDateEvent
import com.boreal.puertocorazon.core.domain.entity.event.getHourEvent
import com.boreal.puertocorazon.core.extension.addLinearHelper
import com.boreal.puertocorazon.core.extension.generateQr
import com.boreal.puertocorazon.core.extension.scrollToPositionCentered
import com.boreal.puertocorazon.core.utils.getImageUri
import com.boreal.puertocorazon.uisystem.R

fun PCShowQrTickets.initElements() {
    binding.apply {
        btnBack.onClick {
            closeFragment()
        }
        loadingImage.setAnimation(R.raw.last_loading)
        loadingImage.repeatCount = LottieDrawable.INFINITE
        loadingImage.playAnimation()
        initAdapter()
        btnAcept.onClick {
            closeFragment()
        }
    }
}

fun PCShowQrTickets.setData(eventModel: PCEventModel) {
    binding.apply {
        eventModel.apply {
            goToMap.onClick {
                openMapsActivity(byLocation = getAddressLatLng())
            }
            txtAddressPlace.text = addressPlace
            txtCity.text = getCity()
            txtHourEvent.text = getHourEvent()
            txtDateEvent.text = getDateEvent()
        }
    }
}

fun PCShowQrTickets.initAdapter() {
    binding.apply {
        adapterRecyclerQrs.submitList(mainViewModel.getAllTicketsClientFiltered(model))
        recyclerViewQrs.apply {
            adapter = adapterRecyclerQrs
            addLinearHelper()
            smoothScrollToPosition(0)
            scrollToPositionCentered()
            if (mainViewModel.getAllTicketsClientFiltered(model).size > 1) {
                itemPercent(.78)
            }
            requestData()
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    when (newState) {
                        AbsListView.OnScrollListener.SCROLL_STATE_IDLE -> {
                            val linearLayoutManager = layoutManager as LinearLayoutManager
                            val itemPosition =
                                linearLayoutManager.findFirstCompletelyVisibleItemPosition()
                            if (itemPosition >= 0 && itemPosition != showTicketViewModel.scrollPosition) {
                                showTicketViewModel.scrollPosition = itemPosition
                                requestData()
                            }
                        }

                        else -> {}
                    }
                }
            })
        }
    }
}

fun PCShowQrTickets.requestData() {
    binding.apply {
        imgShare.onClick {
            getPermissionsStorage {
                Intent().apply {
                    action = Intent.ACTION_SEND
                    mainViewModel.getAllTicketsClientFiltered(model)[showTicketViewModel.scrollPosition].apply {
                        putExtra(
                            Intent.EXTRA_STREAM,
                            idTicket.generateQr()!!
                                .getImageUri(50)
                        )
                        if (isPackage) {
                            if (countAdult > 0 && countChild > 0) {
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "¡Hola, con este boleto puedes acceder al evento " +
                                            "*$nameEvent* este boleto es para *$countAdult adultos* y *$countChild niños / as*, disfruta tu evento!"
                                )
                            } else if (countChild == 0L) {
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "¡Hola, con este boleto puedes acceder al evento *" +
                                            "$nameEvent* este boleto es para *$countAdult adultos*, disfruta tu evento!"
                                )
                            } else if (countAdult == 0L) {
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "¡Hola, con este boleto puedes acceder al evento *" +
                                            "$nameEvent* este boleto es para *$countChild niños / as*, disfruta tu evento!"
                                )
                            }

                        } else {
                            if (countChild == 0L) {
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "¡Hola, con este boleto puedes acceder al evento " +
                                            "*$nameEvent* este boleto es para *$countAdult adulto*, disfruta tu evento!"
                                )
                            } else if (countAdult == 0L) {
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "¡Hola, con este boleto puedes acceder al evento " +
                                            "*$nameEvent* este boleto es para *$countChild niño / a*, disfruta tu evento!"
                                )
                            }
                        }

                    }
                    type = "image/jpeg"
                    startActivity(Intent.createChooser(this, "share"))
                }
            }
        }
        txtCountElements.text = "${showTicketViewModel.scrollPosition + 1} / ${
            mainViewModel.getAllTicketsClientFiltered(model).size
        }"
        mainViewModel.getAllTicketsClientFiltered(model)[showTicketViewModel.scrollPosition].apply {
            mainViewModel.requestSingleEvent(idEvent)
            tvTitleEvent.text = nameEvent
            if (isPackage) {
                imgTypeTicket.changeDrawable(R.drawable.ic_pc_package_ticket)
                tvNamePackage.text = namePackage
                if (countChild > 0) {
                    val childAvailable = (countChild - getAttendedChild()).toInt()
                    tvCountChildren.text = "$childAvailable Niño${
                        if (childAvailable > 1) {
                            "s"
                        } else {
                            ""
                        }
                    }"
                    if (childAvailable > 0) {
                        tvCountChildren.changeTextColor(R.color.black_700)
                    } else {
                        tvCountChildren.changeTextColor(R.color.orange_700)
                    }
                } else {
                    tvCountChildren.hideView()
                }
                if (countAdult > 0) {
                    val adultAvailable = (countAdult - getAttendedAdult())
                    tvCountAdults.text = "$adultAvailable Adulto${
                        if (adultAvailable > 1) {
                            "s"
                        } else {
                            ""
                        }
                    }"
                    if (adultAvailable > 0) {
                        tvCountAdults.changeTextColor(R.color.black_700)
                    } else {
                        tvCountAdults.changeTextColor(R.color.orange_700)
                    }
                } else {
                    tvCountAdults.hideView()
                }
                if (isPackageUsed()) {
                    tvCountAdults.changeTextColor(R.color.orange_700)
                    tvCountChildren.changeTextColor(R.color.orange_700)
                } else {
                    tvCountAdults.changeTextColor(R.color.black_700)
                    tvCountChildren.changeTextColor(R.color.black_700)
                }
            } else {
                imgTypeTicket.changeDrawable(R.drawable.ic_pc_single_ticket)
                tvNamePackage.text = "Boleto ${
                    if (countAdult > countChild) {
                        tvCountAdults.text = if (isAdultUsed()) {
                            tvCountAdults.changeTextColor(R.color.orange_700)
                            "Usado"
                        } else {
                            tvCountAdults.changeTextColor(R.color.green_700)
                            "Disponible"
                        }
                        "Adulto"
                    } else {
                        tvCountAdults.text = if (isChildUsed()) {
                            tvCountAdults.changeTextColor(R.color.orange_700)
                            "Usado"
                        } else {
                            tvCountAdults.changeTextColor(R.color.green_700)
                            "Disponible"
                        }
                        "Infantil"
                    }
                }"
            }
        }
    }
}