package com.boreal.puertocorazon.ticket.ui.showqr

import android.content.Intent
import android.graphics.Bitmap
import android.widget.AbsListView
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieDrawable
import com.boreal.commonutils.application.CUAppInit
import com.boreal.commonutils.extensions.changeTextColor
import com.boreal.commonutils.extensions.invisibleView
import com.boreal.commonutils.extensions.itemPercent
import com.boreal.commonutils.extensions.onClick
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.core.extension.addLinearHelper
import com.boreal.puertocorazon.core.extension.scrollToPositionCentered
import com.boreal.puertocorazon.core.utils.getImageUri
import com.boreal.puertocorazon.ticket.R
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder


fun PCShowQrTickets.initElements() {
    binding.apply {
        btnBack.onClick {
            closeFragment()
        }
        loadingImage.setAnimation(R.raw.last_loading)
        loadingImage.repeatCount = LottieDrawable.INFINITE
        loadingImage.playAnimation()
        initAdapter()
    }
}

fun PCShowQrTickets.setData(eventModel: PCEventModel) {
    binding.apply {
    }
}

fun PCShowQrTickets.initAdapter() {
    binding.apply {
        adapterRecyclerQrs.submitList(listTickets)
        recyclerViewQrs.apply {
            adapter = adapterRecyclerQrs
            addLinearHelper()
            smoothScrollToPosition(0)
            scrollToPositionCentered()
            itemPercent(.78)
            fillData()
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
                                fillData()
                            }
                        }
                        else -> {}
                    }
                }
            })
        }
    }
}

fun PCShowQrTickets.fillData() {
    binding.apply {
        imgShare.onClick {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                listTickets[showTicketViewModel.scrollPosition].apply {
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
            }
            startActivity(Intent.createChooser(intent, "share"))
        }
        txtCountElements.text = "${showTicketViewModel.scrollPosition + 1} / ${listTickets.size}"
        listTickets[showTicketViewModel.scrollPosition].apply {
            mainViewModel.requestSingleEvent(idEvent)
            tvTitleEvent.text = nameEvent
            if (isPackage) {
                tvNamePackage.text = namePackage
                if (countChild > 0) {
                    val countChildren = countChild.toFloat().toInt()
                    tvCountChildren.text = "$countChildren Niño${
                        if (countChildren > 1) {
                            "s"
                        } else {
                            ""
                        }
                    }"
                    val childAvailable = (countChild - attendedChild).toInt()
                    tvCountChildrenAvailable.text = "$childAvailable Niño${
                        if (childAvailable > 1) {
                            "s"
                        } else {
                            ""
                        }
                    }"
                } else {
                    tvCountChildrenAvailable.invisibleView()
                }
                if (countAdult > 0) {
                    val countAdult = countAdult.toFloat().toInt()
                    tvCountAdults.text = "$countAdult Adulto${
                        if (countAdult > 1) {
                            "s"
                        } else {
                            ""
                        }
                    }"
                    val adultAvailable = (countAdult - attendedAdult).toInt()
                    tvCountAdultsAvailable.text = "$adultAvailable Adulto${
                        if (adultAvailable > 1) {
                            "s"
                        } else {
                            ""
                        }
                    }"
                } else {
                    tvCountAdultsAvailable.invisibleView()
                }
                if (isPackageUsed()) {
                    tvCountAdults.changeTextColor(R.color.orange_700)
                    tvCountChildren.changeTextColor(R.color.orange_700)
                }
            } else {
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


fun ImageView.generateQr(generateUrl: String, width: Int = 800, height: Int = 800) =
    try {
        val barcodeEncoder = BarcodeEncoder()
        val bitmap: Bitmap =
            barcodeEncoder.encodeBitmap(generateUrl, BarcodeFormat.QR_CODE, width, height)
        setImageBitmap(bitmap)
        bitmap
    } catch (e: Exception) {
        ContextCompat.getDrawable(context, R.drawable.ic_warning_square)?.toBitmap()
    }

fun String.generateQr(width: Int = 800, height: Int = 800) =
    try {
        val barcodeEncoder = BarcodeEncoder()
        val bitmap: Bitmap =
            barcodeEncoder.encodeBitmap(this, BarcodeFormat.QR_CODE, width, height)
        bitmap
    } catch (e: Exception) {
        ContextCompat.getDrawable(CUAppInit.getAppContext(), R.drawable.ic_warning_square)
            ?.toBitmap()
    }