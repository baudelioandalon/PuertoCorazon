package com.boreal.puertocorazon.core.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.blankj.utilcode.util.FileUtils.getFileByPath
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.UriUtils
import com.boreal.commonutils.application.CUAppInit
import com.boreal.commonutils.globalmethod.randomID
import java.io.ByteArrayOutputStream
import java.io.File

fun Bitmap.getImageUri(levelQuality: Int = 23): Uri {
    return if (Build.VERSION.SDK_INT >= 28) {
        val imagePath = "/storage/emulated/0/pictures"
        val dir = File(imagePath)
        if (!dir.exists()) dir.mkdirs()
        val file = File(dir, randomID() + ".jpg")
        ImageUtils.save(this, file, Bitmap.CompressFormat.JPEG, levelQuality, true)
        UriUtils.file2Uri(getFileByPath(file.path))
    } else {
        val stream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.JPEG, levelQuality, stream)
        val byteArray = stream.toByteArray()
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        Uri.parse(
            MediaStore.Images.Media.insertImage(
                CUAppInit.getAppContext().contentResolver,
                bitmap,
                "title",
                null
            )
        )
    }
}

fun Uri.getImageBitmap(): Bitmap {
    return if (Build.VERSION.SDK_INT >= 28) {
        ImageDecoder.decodeBitmap(
            ImageDecoder.createSource(
                CUAppInit.getAppContext().contentResolver,
                this
            )
        )
    } else {
        MediaStore.Images.Media.getBitmap(
            CUAppInit.getAppContext().contentResolver,
            this
        )
    }
}