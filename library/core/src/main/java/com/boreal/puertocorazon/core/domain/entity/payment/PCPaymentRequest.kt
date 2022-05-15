package com.boreal.puertocorazon.core.domain.entity.payment

data class PCPaymentRequest(
    val countAdult: Long,
    val countChild: Long,
    val idClient: String,
    val idEvent: String,
    val namePackage: String,
    val nameUser: String,
    val email: String,
    val isPackage: Boolean,
    val amount: Long,
    val typeCard: String,
    val lastFour: String,
    val typePayment: String,
    val countItem: Long,
    var token: String = ""
)