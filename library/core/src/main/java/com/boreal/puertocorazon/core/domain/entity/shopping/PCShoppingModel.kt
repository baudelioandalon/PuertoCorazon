package com.boreal.puertocorazon.core.domain.entity.shopping

data class PCShoppingModel(
    val idEvent: String = "",
    val imageEvent: String = "",
    val titleEvent: String = "",
    var countItem: Int = 1,
    val isPackage: Boolean = false,
    val namePackage: String = "",
    val countAdult: Int = 0,
    val countChild: Int = 0,
    val priceElement: Int = 0
)