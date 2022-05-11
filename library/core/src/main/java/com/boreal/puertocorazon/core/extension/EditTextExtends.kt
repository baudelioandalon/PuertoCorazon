package com.boreal.puertocorazon.core.extension

import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.boreal.commonutils.application.CUAppInit
import com.boreal.commonutils.component.roundablelayout.CURoundableLayout
import com.boreal.commonutils.extensions.hideView
import com.boreal.commonutils.extensions.showView
import com.boreal.puertocorazon.core.R


fun EditText.isValidEmail() =
    if (TextUtils.isEmpty(text.toString().replace(" ", ""))) {
        false
    } else {
        android.util.Patterns.EMAIL_ADDRESS.matcher(text.toString().replace(" ", "")).matches()
    }

fun EditText.isValidPassword() = if (TextUtils.isEmpty(text.toString().replace(" ", ""))) {
    false
} else {
    text.toString().replace(" ", "").length > 7
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

fun EditText.onlyText() = text.toString().trim()