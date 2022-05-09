package com.boreal.puertocorazon.login.ui.login

import android.text.TextUtils
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.boreal.commonutils.extensions.onClick
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthLoginEmailModel
import com.boreal.commonutils.extensions.showToast

fun ALoginFragment.initElements() {

    binding.apply {
        txtEmail.setOnEditorActionListener { _, actionId, _ ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                authenticateUser()
                handled = true
            }
            handled
        }
        btnLogin.onClick {
            authenticateUser()
        }
    }
}

fun ALoginFragment.authenticateUser() {
    binding.apply {
        if (txtEmail.isValidEmail() && txtPassword.isValidPassword()) {
            val email = txtEmail.text.toString().replace(" ", "")
            val token = txtPassword.text.toString().replace(" ", "")
            viewModel.requestLogin(AAuthLoginEmailModel(email, token))
        } else {
            showToast("Complete los campos correctamente")
        }
    }

}


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



