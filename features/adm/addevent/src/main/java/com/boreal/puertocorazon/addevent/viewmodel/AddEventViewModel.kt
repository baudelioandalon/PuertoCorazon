package com.boreal.puertocorazon.addevent.viewmodel

import android.net.Uri
import com.boreal.puertocorazon.core.domain.entity.event.PCEventToUploadModel
import com.boreal.puertocorazon.core.domain.entity.event.PCPackageToUploadModel
import com.boreal.puertocorazon.core.utils.CUBaseViewModel

class AddEventViewModel : CUBaseViewModel() {

    private val newEvent = PCEventToUploadModel()
    var requirementsChanged = false

    fun setMainData(title: String, subtitle: String, description: String) {
        newEvent.apply {
            this.title = title
            this.subtitle = subtitle
            this.description = description
        }

    }

    fun setGallery(list: List<Uri>) {
        newEvent.imageGallery = list
    }

    fun setMainImage(mainImage: Uri) {
        newEvent.mainImageUrl = mainImage
    }

    fun setPriceChildren(price: Long) {
        newEvent.priceChild = price
    }

    fun setPriceAdult(price: Long) {
        newEvent.priceAdult = price
    }

    fun setPackages(packageList: List<PCPackageToUploadModel>) {
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

    fun getEventTitle() = newEvent.title
    fun getEventSubtitle() = newEvent.subtitle
    fun getEventDescription() = newEvent.description
    fun getGallery() = newEvent.imageGallery
    fun getMainImage() = newEvent.mainImageUrl
    fun getPriceAdult() = newEvent.priceAdult
    fun getPriceChildren() = newEvent.priceChild
    fun isPriceAdultValid() = newEvent.priceAdult != 0L
    fun isPriceChildValid() = newEvent.priceChild != 0L
    fun getPackages() = newEvent.packages
    fun getAllowedAccesories() = newEvent.allowedAccesories
    fun getAllowedClothing() = newEvent.allowedClothing
    fun getAllowedPeople() = newEvent.allowedPeople

}