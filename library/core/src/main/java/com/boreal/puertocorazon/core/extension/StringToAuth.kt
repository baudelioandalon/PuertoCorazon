package com.boreal.puertocorazon.core.extension

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.boreal.commonutils.application.CUAppInit
import com.boreal.puertocorazon.core.R
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

inline fun <reified T : AAuthModel> String.toModel() = Gson().fromJson(
    "{\"" + Gson().toJson(toString()).replace(":", "\":\"")
        .replace("}", "\"}").substringAfter("proxy[")
        .replace("]\"", "").replace("\"https\":\"", "\"https:").replace("{", "").replace(",", ",\"")
        .replace("}", "") + "}",
    T::class.java
)

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
