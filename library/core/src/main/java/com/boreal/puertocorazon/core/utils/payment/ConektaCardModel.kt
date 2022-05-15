package com.boreal.puertocorazon.core.utils.payment

data class ConektaCardModel(
    val numberCard: String,
    val nameCard: String,
    val cvc: String,
    val exp_month: String,
    val exp_year: String
)