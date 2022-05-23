package com.boreal.puertocorazon.core.domain.entity.payment

import com.boreal.puertocorazon.core.constants.NONE
import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName
import java.util.*

data class PCPackageTicketModel(
    val idPackage: String = "",
    val idTicket: String = "",
    val idPayment: String = "",
    val idClient: String = "",
    val idEvent: String = "",
    val attendedAdult: Long = 0L,
    val attendedChild: Long = 0L,
    val attendedTime: List<Timestamp> = listOf(),
    val payedDate: Timestamp = Timestamp(Date(0L)),
    val countAdult: Long = 0L,
    val countChild: Long = 0L,
    val priceItem: Long = 0L,
    val nameEvent: String = NONE,
    val imageEvent: String = NONE,
    val namePackage: String = "",
    val countItem: Int = 0,
    @JvmField @PropertyName("isPackage")
    val isPackage: Boolean = false
) {
    fun isAdultUsed() = attendedAdult >= countAdult
    fun isChildUsed() = attendedChild >= countChild
    fun isPackageUsed() = isAdultUsed() && isChildUsed()
    fun isUsed() = isPackage && isPackageUsed() ||
            countAdult > countChild && !isPackage && isAdultUsed() ||
            countChild > countAdult && !isPackage && isChildUsed()

    fun isNotUsed() = !isUsed()
}