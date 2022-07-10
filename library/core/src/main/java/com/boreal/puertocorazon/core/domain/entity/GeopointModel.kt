package com.boreal.puertocorazon.core.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Julio Cesar Camacho Silva
 * @since on 24/01/2022 at 1:22
 */
@Parcelize
data class GeopointModel(
    val latitud: Double,
    val longitude: Double,
    val countryCode: String,
    val addressName: String
) : Parcelable
