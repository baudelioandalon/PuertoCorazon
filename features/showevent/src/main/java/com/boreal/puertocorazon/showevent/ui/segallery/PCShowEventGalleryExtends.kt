package com.boreal.puertocorazon.showevent.ui.segallery

import com.boreal.puertocorazon.core.domain.entity.gallery.PCImageItemModel

fun PCShowEventGalleryFragment.initElements() {
    binding.apply {
        initAdapter()
    }
}

fun PCShowEventGalleryFragment.initAdapter() {
    adapterRecyclerImagesGallery.submitList(arrayListOf(PCImageItemModel("https://firebasestorage.googleapis.com/v0/b/puertocorazonapp.appspot.com/o/image_test.jpeg?alt=media&token=146504be-2d82-4358-adfd-f69005234205")))
    binding.mRecyclerImages.adapter = adapterRecyclerImagesGallery
}