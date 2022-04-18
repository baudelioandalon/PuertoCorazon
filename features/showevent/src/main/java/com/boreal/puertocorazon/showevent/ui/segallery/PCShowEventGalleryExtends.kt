package com.boreal.puertocorazon.showevent.ui.segallery

import com.boreal.commonutils.extensions.itemPercent
import com.boreal.puertocorazon.core.domain.entity.gallery.PCImageItemModel
import com.boreal.puertocorazon.showevent.ui.sepackages.addLinearHelper
import com.boreal.puertocorazon.showevent.ui.sepackages.scrollToPositionCentered

fun PCShowEventGalleryFragment.initElements() {
    binding.apply {
        fillData()
    }
}

fun PCShowEventGalleryFragment.fillData() {
    binding.apply {
        mainViewModel.getEventSelected().apply {
            initAdapter(imageGallery.map { PCImageItemModel(it) })
        }
    }
}

fun PCShowEventGalleryFragment.initAdapter(galleryList: List<PCImageItemModel>) {
    adapterRecyclerImagesGallery.submitList(galleryList)
    binding.mRecyclerImages.apply {
        adapter = adapterRecyclerImagesGallery
        addLinearHelper()
        smoothScrollToPosition(0)
        scrollToPositionCentered()
        itemPercent(.88)
    }

}