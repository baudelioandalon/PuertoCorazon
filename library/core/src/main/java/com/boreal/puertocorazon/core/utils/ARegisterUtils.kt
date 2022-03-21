package com.boreal.puertocorazon.core.utils

import android.net.Uri
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.boreal.puertocorazon.core.R
import com.boreal.commonutils.application.CUAppInit
import com.boreal.commonutils.component.roundablelayout.CURoundableLayout
import com.boreal.commonutils.extensions.hideView
import com.boreal.commonutils.extensions.showView

fun EditText.isEmailValid(roundableLayout: CURoundableLayout) =
    if (TextUtils.isEmpty(this.text.toString().trim().trimIndent())) {
        roundableLayout.strokeLineColor =
            ContextCompat.getColor(CUAppInit.getAppContext(), R.color.redError)
        false
    } else {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(this.text.toString().trim().trimIndent())
                .matches()
        ) {
            roundableLayout.strokeLineColor =
                ContextCompat.getColor(CUAppInit.getAppContext(), R.color.blue_edittext)
            true
        } else {
            roundableLayout.strokeLineColor =
                ContextCompat.getColor(CUAppInit.getAppContext(), R.color.redError)
            false
        }

    }


fun EditText.isPhoneValid(roundableLayout: CURoundableLayout) =
    if (TextUtils.isEmpty(text.toString().trim().trimIndent()) || text.toString().trim()
            .trimIndent().length < 10
    ) {
        roundableLayout.strokeLineColor =
            ContextCompat.getColor(CUAppInit.getAppContext(), R.color.redError)
        false
    } else {
        roundableLayout.strokeLineColor =
            ContextCompat.getColor(CUAppInit.getAppContext(), R.color.blue_edittext)
        true
    }


fun EditText.noSpacingText() = text.toString().replace(" ", "").trim().trimIndent()

fun EditText.isNameValid(roundableLayout: CURoundableLayout, messageErrorHolder: TextView) =
    when {
        noSpacingText().isEmpty() -> {
            roundableLayout.strokeLineColor =
                ContextCompat.getColor(CUAppInit.getAppContext(), R.color.redError)
            messageErrorHolder.showView()
            messageErrorHolder.text = "Está vacio el nombre"
            false
        }
        noSpacingText().length < 3 -> {
            roundableLayout.strokeLineColor =
                ContextCompat.getColor(CUAppInit.getAppContext(), R.color.redError)
            messageErrorHolder.showView()
            messageErrorHolder.text = "El nombre es muy corto"
            false
        }
        else -> {
            roundableLayout.strokeLineColor =
                ContextCompat.getColor(CUAppInit.getAppContext(), R.color.blue_edittext)
            messageErrorHolder.hideView()
            messageErrorHolder.text = ""
            true
        }
    }

fun EditText.isLastNameValid(roundableLayout: CURoundableLayout, messageErrorHolder: TextView) =
    when {
        noSpacingText().isEmpty() -> {
            roundableLayout.strokeLineColor =
                ContextCompat.getColor(CUAppInit.getAppContext(), R.color.redError)
            messageErrorHolder.showView()
            messageErrorHolder.text = "Está vacio el apellido"
            false
        }
        noSpacingText().length < 2 -> {
            roundableLayout.strokeLineColor =
                ContextCompat.getColor(CUAppInit.getAppContext(), R.color.redError)
            messageErrorHolder.showView()
            messageErrorHolder.text = "El apellido es muy corto"
            false
        }
        else -> {
            roundableLayout.strokeLineColor =
                ContextCompat.getColor(CUAppInit.getAppContext(), R.color.blue_edittext)
            messageErrorHolder.hideView()
            messageErrorHolder.text = ""
            true
        }
    }


//val imageProfile = viewModelActivity.imageProfileObserver.value != Uri.EMPTY
//val imageComprobante = viewModelActivity.imageComprobanteObserver.value != Uri.EMPTY
//val imageIneFront = viewModelActivity.imageIneFrontObserver.value != Uri.EMPTY
//val imageIneBack = viewModelActivity.imageIneBackObserver.value != Uri.EMPTY
//val isContactValid = it.email.isNotEmpty() && it.phone.isNotEmpty()
//val isPersonalValid = it.name.isNotEmpty() && it.lastName.isNotEmpty()
//val isBirthDayValid = it.birthday.seconds != 0L
//val isOcupationValid = it.ocupation.isNotEmpty()
//val isSalaryValid = it.salaryAmount != 0L

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
