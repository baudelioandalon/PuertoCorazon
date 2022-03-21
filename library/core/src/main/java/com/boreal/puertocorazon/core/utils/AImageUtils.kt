package com.boreal.puertocorazon.core.utils

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.insertImage
import com.boreal.commonutils.application.CUAppInit
import java.io.ByteArrayOutputStream
import java.util.*


fun Bitmap.getImageUri(levelQuality: Int = 100): Uri {
    val bytes = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, levelQuality, bytes)
    val path = insertImage(
        CUAppInit.getAppContext().contentResolver,
        this, UUID.randomUUID().toString() + ".png", "drawing"
    )
    return Uri.parse(path)
}

fun Uri.getImageBitmap(): Bitmap =
    MediaStore.Images.Media.getBitmap(CUAppInit.getAppContext().contentResolver, this)