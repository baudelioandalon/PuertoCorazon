package com.boreal.puertocorazon.core.domain.entity.event

data class PCPackageToUploadModel(
    val empty: Boolean = true,
    val adult: Long = 0L,
    val child: Long = 0L,
    val price: Long = 0L
)