package com.boreal.puertocorazon.core.domain.entity.ticket

import com.boreal.puertocorazon.core.constants.NONE
import com.google.firebase.Timestamp
import java.util.*

data class PCAttendedItem(
    val attendedTime: Timestamp = Timestamp(Date(0L)),
    val attendedType: Boolean = false,
    val ticketType: String = PCTicketType.ADULT.name,
    val imageSaved: String = NONE,
    var selected: Boolean = false
)
