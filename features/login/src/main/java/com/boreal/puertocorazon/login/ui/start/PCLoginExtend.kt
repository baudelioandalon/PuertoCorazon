package com.boreal.puertocorazon.login.ui.start

import androidx.navigation.fragment.findNavController
import com.boreal.commonutils.extensions.onClick
import com.boreal.puertocorazon.core.domain.entity.auth.PCTypeSession
import com.boreal.puertocorazon.login.R

fun PCStartFragment.initElements() {
    binding.apply {
        btnPuertoCorazon.onClick {
            goToLogin(PCTypeSession.NORMAL)
        }
        btnGoogle.onClick {
            goToLogin(PCTypeSession.GOOGLE)
        }
        btnFacebook.onClick {
            goToLogin(PCTypeSession.FACEBOOK)
        }
    }
}

fun PCStartFragment.goToLogin(typeSession: PCTypeSession) {
    viewModel.setTypeSession(typeSession)
    when (typeSession) {
        PCTypeSession.GOOGLE -> {

        }
        PCTypeSession.FACEBOOK -> {

        }
        PCTypeSession.NORMAL -> {
            findNavController().navigate(R.id.action_PCStartFragment_to_ALoginFragment)
        }
    }
}