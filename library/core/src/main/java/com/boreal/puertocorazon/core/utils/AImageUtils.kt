package com.boreal.puertocorazon.core.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.boreal.commonutils.application.CUAppInit
import java.io.ByteArrayOutputStream

fun Bitmap.getImageUri(levelQuality: Int = 23): Uri {
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, levelQuality, stream)
    val byteArray = stream.toByteArray()
    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    val path = MediaStore.Images.Media.insertImage(
        CUAppInit.getAppContext().contentResolver,
        bitmap,
        "title",
        null
    )
    return Uri.parse(path)
}

fun Uri.getImageBitmap(): Bitmap {
    return if (Build.VERSION.SDK_INT < 30) {
        MediaStore.Images.Media.getBitmap(
            CUAppInit.getAppContext().contentResolver,
            this
        )
    } else {
        val source = ImageDecoder.createSource(CUAppInit.getAppContext().contentResolver, this)
        ImageDecoder.decodeBitmap(source)
    }
}