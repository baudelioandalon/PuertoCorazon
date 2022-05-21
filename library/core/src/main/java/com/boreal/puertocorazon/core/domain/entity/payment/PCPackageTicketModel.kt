package com.boreal.puertocorazon.core.domain.entity.payment

import com.boreal.puertocorazon.core.constants.NONE
import com.google.firebase.Timestamp
import java.util.*

data class PCPackageTicketModel(
    val attendedAdult: Long = 0L,
    val attendedChild: Long = 0L,
    val attendedTime: List<Timestamp> = listOf(),
    val payedDate: Timestamp = Timestamp(Date(0L)),
    val countAdult: Long = 0L,
    val countChild: Long = 0L,
    val priceItem: Long = 0L,
    val nameEvent: String = NONE,
    val imageEvent: String = NONE,
    val idClient: String = "",
    val idPayment: String = "",
    val idTicket: String = "",
    val idEvent: String = "",
    val namePackage: String = "",
    val isPackage: Boolean = false
)