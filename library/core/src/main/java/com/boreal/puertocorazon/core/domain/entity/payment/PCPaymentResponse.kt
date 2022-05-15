package com.boreal.puertocorazon.core.domain.entity.payment

data class PCPaymentResponse(
    val code: Int,
    val message: List<String>
)