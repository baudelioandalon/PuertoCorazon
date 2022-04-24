package com.boreal.puertocorazon.addevent.ui.gallery

import com.boreal.commonutils.extensions.setOnSingleClickListener
import com.boreal.puertocorazon.core.component.bottomsheet.ABottomSheetOptionsImageFragment
import com.boreal.puertocorazon.core.domain.entity.gallery.PCImageToUploadItemModel
import com.boreal.puertocorazon.core.extension.addLinearHelper
import com.boreal.puertocorazon.core.extension.scrollToPositionCentered

fun PCGalleryAddEventFragment.initElements() {
    binding.apply {
        initAdapter()
        btnAddGallery.setOnSingleClickListener {
            ABottomSheetOptionsImageFragment { uriReceived ->
                val list = adapterRecyclerImagesGallery.currentList.toTypedArray().toMutableList()
                list.add(PCImageToUploadItemModel(imageToUpdate = uriReceived))
                adapterRecyclerImagesGallery.submitList(list)
            }.show(
                requireActivity().supportFragmentManager,
                "imageoption"
            )
        }
    }
}

fun PCGalleryAddEventFragment.initAdapter() {
    adapterRecyclerImagesGallery.submitList(arrayListOf(PCImageToUploadItemModel()))
    binding.mRecyclerImages.apply {
        adapter = adapterRecyclerImagesGallery
        addLinearHelper()
        smoothScrollToPosition(0)
        scrollToPositionCentered()
    }

}