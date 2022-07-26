package com.boreal.puertocorazon.core.domain.entity.payment

data class PCPaymentRequest(
    val idClient: String,
    val nameUser: String,
    var email: String,
    var emailDefault: String,
    val environment: String,
    val amount: Long,
    val packages: List<PCPackageTicketModel> = listOf()
)