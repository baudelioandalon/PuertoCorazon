package com.boreal.puertocorazon.core.domain.entity.shopping

import com.boreal.commonutils.globalmethod.randomANID
import com.boreal.puertocorazon.core.constants.NONE

data class PCShoppingModel(
    val idEvent: String = "",
    val idPackage: String = randomANID(),
    val imageEvent: String = "",
    val titleEvent: String = "",
    var countItem: Int = 1,
    val isPackage: Boolean = false,
    val namePackage: String = NONE,
    val countAdult: Int = 0,
    val countChild: Int = 0,
    val priceElement: Int = 0
)