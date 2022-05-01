package com.boreal.puertocorazon.core.domain.entity.event

import android.net.Uri
import com.boreal.puertocorazon.core.utils.Constants
import com.google.firebase.Timestamp

data class PCEventToUploadModel(
    val idEvent: String = "NONE",
    var title: String = "",
    var subtitle: String = "",
    var description: String = "",
    val addressPlace: String = "",
    val place: PCLocationModel = PCLocationModel(),
    val eventType: String = "",
    var imageGallery: List<Uri> = listOf(),
    val videoGallery: List<String> = listOf(),
    var packages: List<PCPackageToUploadModel> = listOf(),
    var priceAdult: Long = 0L,
    var priceChild: Long = 0L,
    val readyToShow: Boolean = true,
    var schedule: List<PCScheduleModel> = listOf(),
    val creationDate: Timestamp = Timestamp(0L, 0),
    val instructionId: String = "",
    val instructorName: String = "",
    val instructorImageUrl: String = "",
    var mainImageUrl: Uri = Uri.EMPTY,
    val capacity: Long = 0L,
    val homeImageUrl: Uri = Uri.EMPTY,
    var allowedPeople: List<String> = Constants.allowedPeopleList.map { it.name },
    var allowedAccesories: List<String> = Constants.allowedAccesoriesList.map { it.name },
    var allowedClothing: List<String> = Constants.allowedClothesList.map { it.name }
)