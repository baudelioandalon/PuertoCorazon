package com.boreal.puertocorazon.core.domain.entity.payment

data class PCCardModel(
    val alias: String = "",
    val typeCard: String = PCTypeCard.NONE.name,
    val token: String = "",
    val cardNumber: String = "",
    val cvv: String = "",
    val ownerName: String = "",
    val default: Boolean = false
)