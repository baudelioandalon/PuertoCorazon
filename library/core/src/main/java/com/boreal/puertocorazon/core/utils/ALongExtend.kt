package com.boreal.puertocorazon.core.utils

import java.text.NumberFormat
import java.util.*

fun Long.formatCurrency(noSymbol: Boolean = false, textFirst: String = "") = if (!noSymbol) {
    if (textFirst.isNotEmpty()) {
        "$textFirst " + NumberFormat.getCurrencyInstance(Locale.US)
            .format((this)).replace("€", "").trim().trimIndent().takeWhile {
                it != '.'
            }
    } else {
        NumberFormat.getCurrencyInstance(Locale.US)
            .format((this)).replace("€", "").trim().trimIndent().takeWhile {
                it != '.'
            }
    }

} else {
    NumberFormat.getCurrencyInstance(Locale.US)
        .format((this)).replace("€", "").trim().trimIndent().takeWhile { it != '.' }
        .replace("$", "")
}