package com.boreal.puertocorazon.core.domain.entity.event

data class PCPackageToUploadModel(
    var empty: Boolean = true,
    var titlePackage: String = "",
    var adult: Long = 0L,
    var child: Long = 0L,
    var price: Long = 0L
)