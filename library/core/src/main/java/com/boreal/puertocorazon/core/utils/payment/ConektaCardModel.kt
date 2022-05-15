package com.boreal.puertocorazon.core.utils.payment

import com.boreal.puertocorazon.core.domain.entity.payment.PCTypeCard

data class ConektaCardModel(
    val numberCard: String,
    val nameCard: String,
    val cvc: String,
    val exp_month: String,
    val exp_year: String
) {
    fun typeCard() = if (numberCard.startsWith("4") && numberCard.length < 16) {
        PCTypeCard.VISA.name
    } else if (numberCard.startsWith("5") && numberCard.length < 16) {
        PCTypeCard.MASTERCARD.name
    } else {
        PCTypeCard.AMEX.name
    }

    fun lastFour() = if (numberCard.length >= 4) numberCard.takeLast(4) else ""

    fun expirationDate() = "$exp_month/$exp_year"
}