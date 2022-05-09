package com.boreal.puertocorazon.addevent.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.boreal.commonutils.globalmethod.randomID
import com.boreal.puertocorazon.addevent.usecase.AddEventUseCase
import com.boreal.puertocorazon.core.domain.entity.AFirestoreSetResponse
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.event.*
import com.boreal.puertocorazon.core.domain.entity.requirements.PCRequirementEnum
import com.boreal.puertocorazon.core.usecase.UseCase
import com.boreal.puertocorazon.core.utils.CUBaseViewModel
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUFirestoreErrorEnum
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class AddEventViewModel(private val addEventUseCase: UseCase<AddEventUseCase.Input, AddEventUseCase.Output>) :
    CUBaseViewModel() {

    private var newEvent = PCEventModel(
        title = "Titilo de prueba",
        subtitle = "Subtitulo de prueba",
        description = "Esto es una descripcion de prueba",
        addressPlace = "Calle miramar#1195, San esteban",
        place = PCLocationModel(latitude = 0L, 5L),
        eventType = "PARTICULAR",
        imageGallery = listOf(),
        videoGallery = listOf(),
        packages = listOf(
            PCPackageModel(
                titlePackage = "Paquete 1",
                adult = 2L,
                child = 1L,
                price = 500L
            )
        ),
        priceAdult = 300L,
        priceChild = 200L,
        readyToShow = false,
        schedule = listOf(
            PCScheduleModel(
                idTime = 1L,
                startTime = Timestamp.now(),
                endTime = Timestamp.now()
            )
        ),
        creationDate = Timestamp.now(),
        instructionId = "NONE",
        instructorName = "NONE",
        instructorImageUrl = "NONE",
        mainImageUrl = "NONE",
        capacity = 50L,
        homeImageUrl = "NONE",
        allowedPeople = listOf(PCRequirementEnum.OLD.name),
        allowedAccesories = listOf(PCRequirementEnum.NECKLACE.name),
        allowedClothing = listOf(PCRequirementEnum.LARGE_SHIRT.name)
    )

    private var eventImageToUpload = PCEventToUploadModel()

    var requirementsChanged = false

    val addEvent: LiveData<AFirestoreSetResponse<PCEventModel, CUFirestoreErrorEnum>>
        get() = _addEvent
    private val _addEvent =
        MutableLiveData<AFirestoreSetResponse<PCEventModel, CUFirestoreErrorEnum>>()

    fun setMainData(title: String, subtitle: String, description: String) {
        newEvent.apply {
            this.title = title
            this.subtitle = subtitle
            this.description = description
        }
    }

    fun setGallery(list: List<Uri>) {
        eventImageToUpload.imageGallery = list
    }

    fun setMainImage(mainImageUri: Uri) {
        eventImageToUpload.mainImageUrl = mainImageUri
    }

    fun setHomeImage(homeImageUri: Uri) {
        eventImageToUpload.homeImageUrl = homeImageUri
    }

    fun setPriceChildren(price: Long) {
        newEvent.priceChild = price
    }

    fun setPriceAdult(price: Long) {
        newEvent.priceAdult = price
    }

    fun setPackages(packageList: List<PCPackageModel>) {
        newEvent.packages = packageList
    }

    fun setAllowedAccesories(allowedAccesories: List<String>) {
        newEvent.allowedAccesories = allowedAccesories
    }

    fun setAllowedClothing(allowedClothing: List<String>) {
        newEvent.allowedClothing = allowedClothing
    }

    fun setAllowedPeople(allowedPeople: List<String>) {
        newEvent.allowedPeople = allowedPeople
    }

    fun setSchedule(schedule: List<PCScheduleModel>) {
        newEvent.schedule = schedule
    }

    fun setEvent() {
        executeFlow {
            _addEvent.value = AFirestoreSetResponse(
                status = AFirestoreStatusRequest.LOADING
            )
            addEventUseCase.execute(AddEventUseCase.Input(newEvent))
                .catch { cause: Throwable ->
                    cause
                    _addEvent.value = AFirestoreSetResponse(
                        status = AFirestoreStatusRequest.FAILURE
                    )
                }.collect {
                    _addEvent.value = it.response
                }
        }
    }

    fun getEventTitle() = newEvent.title
    fun getEventSubtitle() = newEvent.subtitle
    fun getEventDescription() = newEvent.description
    fun getGallery() = eventImageToUpload.imageGallery
    fun getMainImage() = eventImageToUpload.mainImageUrl
    fun getHomeImage() = eventImageToUpload.homeImageUrl
    fun getPriceAdult() = newEvent.priceAdult
    fun getPriceChildren() = newEvent.priceChild
    fun isPriceAdultValid() = newEvent.priceAdult != 0L
    fun isPriceChildValid() = newEvent.priceChild != 0L
    fun getPackages() = newEvent.packages
    fun getAllowedAccesories() = newEvent.allowedAccesories
    fun getAllowedClothing() = newEvent.allowedClothing
    fun getAllowedPeople() = newEvent.allowedPeople
    fun getSchedule() = newEvent.schedule
    fun isScheduleValid() = getSchedule().isNotEmpty()
    fun resetViewModel() {
        newEvent = PCEventModel()
        requirementsChanged = false
        eventImageToUpload = PCEventToUploadModel()
        _addEvent.value = AFirestoreSetResponse(
            status = AFirestoreStatusRequest.NONE
        )
    }

}