package com.boreal.puertocorazon.login.ui.start

import android.content.IntentSender
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.boreal.commonutils.extensions.onClick
import com.boreal.commonutils.extensions.showToast
import com.boreal.puertocorazon.core.domain.entity.auth.PCTypeSession
import com.boreal.puertocorazon.login.R
import com.boreal.puertocorazon.login.ui.start.PCStartFragment.Companion.REQ_GOOGLE
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes

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
            oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(requireActivity()) { result ->
                    try {
                        startIntentSenderForResult(
                            result.pendingIntent.intentSender, REQ_GOOGLE,
                            null, 0, 0, 0, null
                        )
                    } catch (e: IntentSender.SendIntentException) {
                        showToast("Algo salio mal")
                        Log.e("LOGIN_GOOGLE", "Couldn't start One Tap UI: ${e.localizedMessage}")
                    }
                }.addOnFailureListener(requireActivity()) { e ->
                    if (e is ApiException) {

                        when (e.statusCode) {
                            CommonStatusCodes.CANCELED -> {
                                Log.e("LOGIN_GOOGLE", e.localizedMessage ?: "ErrorDefault")
                                showToast("Hubo muchas cancelaciones de inicio con google, reintente de nuevo en 24 horas.")
                            }
                            CommonStatusCodes.NETWORK_ERROR -> {
                                showToast("Hubo un error de red, favor de verificar su internet.")
                                Log.e("LOGIN_GOOGLE", e.localizedMessage ?: "ErrorDefault")
                            }
                            CommonStatusCodes.DEVELOPER_ERROR -> {
                                showToast(
                                    "Hubo un error en los servicios de Google, por favor," +
                                            " reinstale la aplicación y el problema será resuelto, lamentamos el inconveniente.",
                                    Toast.LENGTH_LONG
                                )
                                Log.e("LOGIN_GOOGLE", e.localizedMessage ?: "ErrorDefault")
                            }
                        }

                    } else {
                        showToast("Algo salio mal")
                        Log.e("LOGIN_GOOGLE", e.localizedMessage ?: "ErrorDefault")
                    }

                }
        }
        PCTypeSession.FACEBOOK -> {

        }
        PCTypeSession.NORMAL -> {
            findNavController().navigate(R.id.action_PCStartFragment_to_ALoginFragment)
        }
    }
}