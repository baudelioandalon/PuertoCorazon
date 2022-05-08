package com.boreal.puertocorazon.core.domain.entity.event

import android.net.Uri

data class PCEventToUploadModel(
    var imageGallery: List<Uri> = listOf(),
    var mainImageUrl: Uri = Uri.EMPTY,
    var homeImageUrl: Uri = Uri.EMPTY,
)