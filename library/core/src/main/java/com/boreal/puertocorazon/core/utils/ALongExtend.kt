package com.boreal.puertocorazon.core.utils

import java.text.NumberFormat
import java.util.*

fun Long.formatCurrency(noSymbol: Boolean = false) = if (!noSymbol) {
    NumberFormat.getCurrencyInstance(Locale.US)
        .format((this)).replace("€", "").trim().trimIndent().takeWhile { it != '.' }
} else {
    NumberFormat.getCurrencyInstance(Locale.US)
        .format((this)).replace("€", "").trim().trimIndent().takeWhile { it != '.' }
        .replace("$", "")
}