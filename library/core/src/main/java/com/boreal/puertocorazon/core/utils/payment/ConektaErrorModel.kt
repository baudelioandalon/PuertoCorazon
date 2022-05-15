package com.boreal.puertocorazon.core.utils.payment

data class ConektaErrorModel(
    val details: List<Detail>,
    val log_id: String,
    val `object`: String,
    val type: String
)