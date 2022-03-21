package com.boreal.puertocorazon.core.domain.entity.auth

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Identities(
    val email: List<String> = arrayListOf()
) : Parcelable