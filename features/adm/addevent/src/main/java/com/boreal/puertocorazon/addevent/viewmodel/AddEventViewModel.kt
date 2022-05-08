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

    private val newEventTest = PCEventModel(
        title = "Titilo de prueba",
        subtitle = "Subtitulo de prueba",
        idEvent = randomID(),
        description = "Esto es una descripcion de prueba",
        addressPlace = "Calle miramar#1195, San esteban",
        place = PCLocationModel(latitude = 0L, 5L),
        eventType = "PARTICULAR",
        imageGallery = listOf("NONE"),
        videoGallery = listOf("NONE"),
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

    private val eventImageToUpload = PCEventToUploadModel()

    var requirementsChanged = true

    val addEvent: LiveData<AFirestoreSetResponse<PCEventModel, CUFirestoreErrorEnum>>
        get() = _addEvent
    private val _addEvent =
        MutableLiveData<AFirestoreSetResponse<PCEventModel, CUFirestoreErrorEnum>>()

    fun setMainData(title: String, subtitle: String, description: String) {
        newEventTest.apply {
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
        newEventTest.priceChild = price
    }

    fun setPriceAdult(price: Long) {
        newEventTest.priceAdult = price
    }

    fun setPackages(packageList: List<PCPackageModel>) {
        newEventTest.packages = packageList
    }

    fun setAllowedAccesories(allowedAccesories: List<String>) {
        newEventTest.allowedAccesories = allowedAccesories
    }

    fun setAllowedClothing(allowedClothing: List<String>) {
        newEventTest.allowedClothing = allowedClothing
    }

    fun setAllowedPeople(allowedPeople: List<String>) {
        newEventTest.allowedPeople = allowedPeople
    }

    fun setSchedule(schedule: List<PCScheduleModel>) {
        newEventTest.schedule = schedule
    }

    fun setEvent() {
        executeFlow {
            _addEvent.value = AFirestoreSetResponse(
                status = AFirestoreStatusRequest.LOADING
            )
            addEventUseCase.execute(AddEventUseCase.Input(newEventTest))
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

    fun getEventTitle() = newEventTest.title
    fun getEventSubtitle() = newEventTest.subtitle
    fun getEventDescription() = newEventTest.description
    fun getGallery() = eventImageToUpload.imageGallery
    fun getMainImage() = eventImageToUpload.mainImageUrl
    fun getHomeImage() = eventImageToUpload.homeImageUrl
    fun getPriceAdult() = newEventTest.priceAdult
    fun getPriceChildren() = newEventTest.priceChild
    fun isPriceAdultValid() = newEventTest.priceAdult != 0L
    fun isPriceChildValid() = newEventTest.priceChild != 0L
    fun getPackages() = newEventTest.packages
    fun getAllowedAccesories() = newEventTest.allowedAccesories
    fun getAllowedClothing() = newEventTest.allowedClothing
    fun getAllowedPeople() = newEventTest.allowedPeople
    fun getSchedule() = newEventTest.schedule
    fun isScheduleValid() = getSchedule().isNotEmpty()


}