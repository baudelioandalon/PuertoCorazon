package com.boreal.puertocorazon.core.domain.entity.event

import com.google.firebase.Timestamp

data class PCEventModel(
    val idEvent: String = "NONE",
    var title: String = "",
    var subtitle: String = "",
    var description: String = "",
    val addressPlace: String = "",
    val place: PCLocationModel = PCLocationModel(),
    val eventType: String = "",
    val imageGallery: List<String> = listOf(),
    val videoGallery: List<String> = listOf(),
    val packages: List<PCPackageModel> = listOf(),
    val priceAdult: Long = 0L,
    val priceChild: Long = 0L,
    val readyToShow: Boolean = true,
    val schedule: List<PCScheduleModel> = listOf(),
    val creationDate: Timestamp = Timestamp(0L, 0),
    val instructionId: String = "",
    val instructorName: String = "",
    val instructorImageUrl: String = "",
    val mainImageUrl: String = "",
    val capacity: Long = 0L,
    val homeImageUrl: String = "",
    val allowedPeople: List<String> = listOf(),
    val allowedAccesories: List<String> = listOf(),
    val allowedClothing: List<String> = listOf()
)