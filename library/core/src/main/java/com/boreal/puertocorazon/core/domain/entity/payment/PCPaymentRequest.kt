package com.boreal.puertocorazon.core.domain.entity.payment

data class PCPaymentRequest(
    val idClient: String,
    val nameUser: String,
    val email: String,
    val amount: Long,
    val typeCard: String,
    val lastFour: String,
    val typePayment: String,
    val phone: String,
    var token: String = "",
    val saveCard: Boolean = true,
    val aliasCard: String,
    val expirationDate: String,
    val digitsCard: String,
    var emailLocal: String,
    var environmentLocal: String,
    val packages: List<PCPackageTicketModel> = listOf()
)