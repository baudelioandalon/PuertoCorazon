package com.boreal.puertocorazon.core.component.maputils.model

import com.boreal.puertocorazon.core.component.maputils.model.ListClass

data class ModelPlaces(
    val id: Int,
    val status: String,
    val predictions: ArrayList<ListClass>
)