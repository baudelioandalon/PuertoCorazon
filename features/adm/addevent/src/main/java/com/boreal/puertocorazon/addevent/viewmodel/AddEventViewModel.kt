package com.boreal.puertocorazon.addevent.viewmodel

import android.net.Uri
import com.boreal.puertocorazon.core.domain.entity.event.PCEventToUploadModel
import com.boreal.puertocorazon.core.utils.CUBaseViewModel

class AddEventViewModel : CUBaseViewModel() {

    private val newEvent = PCEventToUploadModel()

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

    fun getEventTitle() = newEvent.title
    fun getEventSubtitle() = newEvent.subtitle
    fun getEventDescription() = newEvent.description
    fun getGallery() = newEvent.imageGallery
    fun getMainImage() = newEvent.mainImageUrl

}