package com.boreal.puertocorazon.login.ui.start

import android.content.IntentSender
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.boreal.commonutils.extensions.onClick
import com.boreal.puertocorazon.core.domain.entity.auth.PCTypeSession
import com.boreal.puertocorazon.login.R
import com.boreal.puertocorazon.login.ui.start.PCStartFragment.Companion.REQ_GOOGLE
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity

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

fun PCStartFragment.initAnimations() {
    binding.imgOne.startAnimation(
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.slide_right
        )
    )
    binding.imgTwo.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in))
    binding.imgLogo.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in))
    binding.imgTop.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down))
}

fun PCStartFragment.goToLogin(typeSession: PCTypeSession) {
    viewModel.setTypeSession(typeSession)
    when (typeSession) {
        PCTypeSession.GOOGLE -> {
            oneTapClient = Identity.getSignInClient(requireActivity())

            val signInRequest: BeginSignInRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(
                    BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build()
                )
                .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.your_web_client_id))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(false)
                        .build()
                )
                .build()

            oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(requireActivity()) { result ->
                    result
                    try {
                        startIntentSenderForResult(
                            result.pendingIntent.intentSender, REQ_GOOGLE,
                            null, 0, 0, 0, null
                        )
                    } catch (e: IntentSender.SendIntentException) {
                        Log.e("LOGIN_GOOGLE", "Couldn't start One Tap UI: ${e.localizedMessage}")
                    }
                }.addOnFailureListener(requireActivity()) { e ->
                    // No saved credentials found. Launch the One Tap sign-up flow, or
                    // do nothing and continue presenting the signed-out UI.
                    e

                    Log.e("LOGIN_GOOGLE", e.localizedMessage ?: "ErrorDefault")
                }.addOnCompleteListener {
                    it
                }

        }
        PCTypeSession.FACEBOOK -> {

        }
        PCTypeSession.NORMAL -> {
            findNavController().navigate(R.id.action_PCStartFragment_to_ALoginFragment)
        }
    }
}