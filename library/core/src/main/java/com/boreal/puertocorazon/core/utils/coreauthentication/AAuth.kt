package com.boreal.puertocorazon.core.utils.coreauthentication

import com.google.firebase.auth.FirebaseAuth

object AAuth {
    val authInstance by lazy {
        FirebaseAuth.getInstance()
    }
}