package com.boreal.puertocorazon.core.domain.entity.payment

import com.google.firebase.Timestamp
import java.util.*

data class PCPaymentRequest(
//    val namePackage: String,
//    val isPackage: Boolean,
//    val idEvent: String,
    val idClient: String,
    val nameUser: String,
    val email: String,
    val amount: Long,
    val typeCard: String,
    val lastFour: String,
    val typePayment: String,
    val phone: String,
    var token: String = "",
    val saveCard: Boolean = true,
    val aliasCard: String,
    val expirationDate: String,
    val digitsCard: String,
    val emailLocal: String,
    val environmentLocal: String,
    val packages: List<PCPackagePaymentModel> = listOf()
)

data class PCPackagePaymentModel(
    val attendedAdult: Long = 0L,
    val attendedChild: Long = 0L,
    val attendedTime: List<Timestamp> = listOf(),
    val payedDate: Timestamp = Timestamp(Date(0L)),
    val countAdult: Long = 0L,
    val countChild: Long = 0L,
    val idClient: String,
    val idPayment: String = "",
    val idTicket: String = "",
    val idEvent: String,
    val namePackage: String,
    val isPackage: Boolean
)