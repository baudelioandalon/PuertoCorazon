package com.boreal.puertocorazon.ticket.ui.showqr

import android.graphics.Bitmap
import android.widget.ImageView
import com.boreal.commonutils.extensions.changeDrawable
import com.boreal.commonutils.extensions.onClick
import com.boreal.puertocorazon.core.extension.addLinearHelper
import com.boreal.puertocorazon.core.extension.scrollToPositionCentered
import com.boreal.puertocorazon.ticket.R
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

fun PCShowQrTickets.initElements() {
    binding.apply {
        btnBack.onClick {
            closeFragment()
        }
        initAdapter()
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
        }
    }
}


fun ImageView.generateQr(generateUrl: String, width: Int = 800, height: Int = 800) {
    try {
        val barcodeEncoder = BarcodeEncoder()
        val bitmap: Bitmap =
            barcodeEncoder.encodeBitmap(generateUrl, BarcodeFormat.QR_CODE, width, height)
        setImageBitmap(bitmap)
    } catch (e: Exception) {
        changeDrawable(R.drawable.ic_warning_square)
    }
}