package com.boreal.puertocorazon.core.domain.entity.event

import com.boreal.commonutils.globalmethod.randomANID

data class PCPackageModel(
    val idPackage: String = randomANID(),
    val titlePackage: String = "",
    val adult: Long = 0L,
    val child: Long = 0L,
    val price: Long = 0L
)