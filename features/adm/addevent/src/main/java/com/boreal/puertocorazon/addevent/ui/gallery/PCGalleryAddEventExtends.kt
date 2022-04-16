package com.boreal.puertocorazon.addevent.ui.gallery

import com.boreal.puertocorazon.core.domain.entity.gallery.PCImageToUploadItemModel

fun PCGalleryAddEventFragment.initElements() {
    binding.apply {
        initAdapter()
    }
}

fun PCGalleryAddEventFragment.initAdapter() {
    adapterRecyclerImagesGallery.submitList(arrayListOf(PCImageToUploadItemModel()))
    binding.mRecyclerImages.adapter = adapterRecyclerImagesGallery
}