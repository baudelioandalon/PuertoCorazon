package com.boreal.puertocorazon.addevent.ui.gallery

import com.boreal.puertocorazon.core.domain.entity.gallery.PCImageItemModel

fun PCGalleryAddEventFragment.initElements() {
    binding.apply {
        initAdapter()
    }
}

fun PCGalleryAddEventFragment.initAdapter() {
    adapterRecyclerImagesGallery.submitList(arrayListOf(PCImageItemModel()))
    binding.mRecyclerImages.adapter = adapterRecyclerImagesGallery
}