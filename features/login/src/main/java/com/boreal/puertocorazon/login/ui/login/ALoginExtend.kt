package com.boreal.puertocorazon.login.ui.login

import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import androidx.navigation.fragment.findNavController
import com.boreal.commonutils.extensions.onClick
import com.boreal.commonutils.extensions.showToast
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthLoginEmailModel
import com.boreal.puertocorazon.core.extension.isValidEmail
import com.boreal.puertocorazon.core.extension.isValidPassword
import com.boreal.puertocorazon.login.R

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

        btnBack.onClick {
            mainViewModel.resetLogin = false
            findNavController().popBackStack(R.id.PCStartFragment, false)
        }
        if(mainViewModel.logOut){
            mainViewModel.logOut = false
            findNavController().popBackStack(R.id.PCStartFragment, false)
        }
    }
}

fun ALoginFragment.initAnimations() {
    binding.apply {
        imgOne.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in))
        imgTwo.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.slide_left))
        imgLogo.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in))
        imgTop.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down))
    }
}

fun ALoginFragment.authenticateUser() {
    binding.apply {
        if (txtEmail.isValidEmail() && txtPassword.isValidPassword()) {
            val email = txtEmail.text.toString().replace(" ", "")
            val token = txtPassword.text.toString().replace(" ", "")
            viewModel.requestNormalLogin(AAuthLoginEmailModel(email, token))
        } else {
            showToast("Complete los campos correctamente")
        }
    }

}