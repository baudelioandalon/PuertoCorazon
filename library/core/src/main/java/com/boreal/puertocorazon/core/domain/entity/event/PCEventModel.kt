package com.boreal.puertocorazon.core.domain.entity.event

import com.boreal.commonutils.globalmethod.randomANID
import com.google.firebase.Timestamp

data class PCEventModel(
    val idEvent: String = randomANID(),
    var title: String = "",
    var subtitle: String = "",
    var description: String = "",
    var addressPlace: String = "",
    var place: PCLocationModel = PCLocationModel(),
    val eventType: String = "",
    var imageGallery: List<String> = listOf(),
    val videoGallery: List<String> = listOf(),
    var packages: List<PCPackageModel> = listOf(),
    var priceAdult: Long = 0L,
    var priceChild: Long = 0L,
    val readyToShow: Boolean = true,
    var schedule: List<PCScheduleModel> = listOf(),
    val creationDate: Timestamp = Timestamp(0L, 0),
    val instructionId: String = "",
    val instructorName: String = "",
    val instructorImageUrl: String = "",
    var mainImageUrl: String = "",
    val capacity: Long = 0L,
    val homeImageUrl: String = "",
    var allowedPeople: List<String> = listOf(),
    var allowedAccesories: List<String> = listOf(),
    var allowedClothing: List<String> = listOf()
)