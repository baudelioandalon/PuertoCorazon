package com.boreal.puertocorazon.core.domain.entity.event

import com.google.firebase.Timestamp

data class PCScheduleModel(
    val idTime: Long = 0L,
    val startTime: Timestamp = Timestamp(0L, 0),
    val endTime: Timestamp = Timestamp(0L, 0)
)