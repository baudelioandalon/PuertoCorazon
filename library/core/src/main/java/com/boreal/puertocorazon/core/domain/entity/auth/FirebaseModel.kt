package com.boreal.puertocorazon.core.domain.entity.auth

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FirebaseModel(
    val identities: Identities? = Identities(),
    val sign_in_provider: String = ""
) : Parcelable