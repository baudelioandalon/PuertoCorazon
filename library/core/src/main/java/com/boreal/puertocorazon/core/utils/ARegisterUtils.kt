package com.boreal.puertocorazon.core.utils

import android.net.Uri
import android.text.Editable
import android.widget.TextView
import com.boreal.commonutils.extensions.hideView
import com.boreal.commonutils.extensions.showView
import com.google.firebase.Timestamp

fun TextView.onlyText() = text.toString().trim()
fun Editable.onlyText() = toString().trim()
fun String.onlyText() = trim()

fun String.onlyCardNumber() = onlyText().trimIndent()
    .replace("-", "").replace(" ", "")

fun Editable.onlyCardNumber() = toString().trim().onlyCardNumber()

fun String.validCardNumber() = if (onlyText().isNotEmpty()) {
    onlyCardNumber().length == 16
} else {
    false
}

fun Editable.validCardNumber() = onlyText().validCardNumber()

fun String.validMonth() = if (onlyText().isNotEmpty()) {
    onlyText().toInt() < 13
} else {
    false
}

fun Editable.validMonth() = onlyText().validMonth()

fun String.validCvv(isAmex: Boolean = false) = if (onlyText().isNotEmpty()) {
    if (!isAmex) onlyText().length == 3 else onlyText().length == 4
} else {
    false
}

fun Editable.validCvv(isAmex: Boolean = false) = onlyText().validCvv(isAmex)

fun String.validYear() = if (onlyText().isNotEmpty() && onlyText().length == 2) {
    onlyText().toInt() >= Timestamp.now().getYear().takeLast(2).toInt()
} else {
    false
}

fun Editable.validYear() = onlyText().validYear()

fun TextView.clearText() {
    text = ""
}

inline fun <reified T> TextView.toNumber(): T {
    return when (T::class) {
        Int::class -> onlyText().toInt() as T
        Double::class -> onlyText().toDouble() as T
        Long::class -> onlyText().toLong() as T
        else -> throw IllegalStateException("Unknown Generic Type")
    }
}

fun Uri.isValidImage(messageErrorHolder: TextView, errorMessage: String) = when (this) {
    Uri.EMPTY -> {
        messageErrorHolder.showView()
        messageErrorHolder.text = errorMessage
        false
    }
    else -> {
        messageErrorHolder.hideView()
        messageErrorHolder.text = ""
        true
    }
}

fun String.isEmailRegisterValid(messageErrorHolder: TextView, errorMessage: String) =
    when (this) {
        "" -> {
            messageErrorHolder.showView()
            messageErrorHolder.text = errorMessage
            false
        }
        else -> {
            messageErrorHolder.hideView()
            messageErrorHolder.text = ""
            true
        }

    }

fun String.isPhoneRegisterValid(messageErrorHolder: TextView, errorMessage: String) =
    when (this) {
        "" -> {
            messageErrorHolder.showView()
            messageErrorHolder.text = errorMessage
            false
        }
        else -> {
            messageErrorHolder.hideView()
            messageErrorHolder.text = ""
            true
        }

    }

fun String.isNameRegisterValid(messageErrorHolder: TextView, errorMessage: String) =
    when (this) {
        "" -> {
            messageErrorHolder.showView()
            messageErrorHolder.text = errorMessage
            false
        }
        else -> {
            messageErrorHolder.hideView()
            messageErrorHolder.text = ""
            true
        }

    }

fun String.isLastNameRegisterValid(messageErrorHolder: TextView, errorMessage: String) =
    when (this) {
        "" -> {
            messageErrorHolder.showView()
            messageErrorHolder.text = errorMessage
            false
        }
        else -> {
            messageErrorHolder.hideView()
            messageErrorHolder.text = ""
            true
        }

    }

fun String.isSexRegisterValid(messageErrorHolder: TextView, errorMessage: String) =
    when (this) {
        "" -> {
            messageErrorHolder.showView()
            messageErrorHolder.text = errorMessage
            false
        }
        else -> {
            messageErrorHolder.hideView()
            messageErrorHolder.text = ""
            true
        }

    }

fun String.isOcupationRegisterValid(messageErrorHolder: TextView, errorMessage: String) =
    when (this) {
        "" -> {
            messageErrorHolder.showView()
            messageErrorHolder.text = errorMessage
            false
        }
        else -> {
            messageErrorHolder.hideView()
            messageErrorHolder.text = ""
            true
        }

    }

fun Long.isSalaryAmountRegisterValid(messageErrorHolder: TextView, errorMessage: String) =
    when (this) {
        0L -> {
            messageErrorHolder.showView()
            messageErrorHolder.text = errorMessage
            false
        }
        else -> {
            messageErrorHolder.hideView()
            messageErrorHolder.text = ""
            true
        }

    }
