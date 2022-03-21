package com.boreal.puertocorazon.core.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/****
 * Project: Altemis
 * From: com.boreal.altemis.generic.ui.activities.maputils.model
 * Created by Julio Cesar Camacho Silva on 24/01/2022 at 1:22
 * More info: https://www.facebook.com/juliocesar.camachosilva/
 * All rights reserved 2022.
 ***/
@Parcelize
data class GeopointModel(
    val latitud: Double,
    val longitude: Double,
    val countryCode: String,
    val address: String
) : Parcelable
