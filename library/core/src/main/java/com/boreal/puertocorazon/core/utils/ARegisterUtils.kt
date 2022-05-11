package com.boreal.puertocorazon.core.utils

import android.net.Uri
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.boreal.commonutils.application.CUAppInit
import com.boreal.commonutils.component.roundablelayout.CURoundableLayout
import com.boreal.commonutils.extensions.hideView
import com.boreal.commonutils.extensions.showView
import com.boreal.puertocorazon.core.R

fun TextView.onlyText() = text.toString().trim()
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
