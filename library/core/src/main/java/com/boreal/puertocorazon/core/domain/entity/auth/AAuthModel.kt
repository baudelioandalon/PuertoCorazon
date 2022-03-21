package com.boreal.puertocorazon.core.domain.entity.auth

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AAuthModel(
    val aud: String = "",
    val auth_time: Int = 0,
    val dateCreated: Long = 0L,
    val email: String = "",
    val email_verified: Boolean = false,
    val exp: Int = 0,
    val firebase: FirebaseModel? = FirebaseModel(),
    val iat: Int = 0,
    val iss: String = "",
    val sub: String = "",
    val userType: String = "",
    val user_id: String = ""
): Parcelable