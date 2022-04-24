package com.boreal.puertocorazon.addevent.ui.gallery

import android.net.Uri
import com.boreal.commonutils.extensions.itemPercent
import com.boreal.commonutils.extensions.setOnSingleClickListener
import com.boreal.puertocorazon.addevent.R
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
        btnSave.setOnSingleClickListener {
            if (adapterRecyclerImagesGallery.currentList.toMutableList()
                    .indexOfFirst { it.imageToUpdate == Uri.EMPTY } != -1
            ) {
                tvErrorMessage.text = getString(R.string.select_photo_text)
                return@setOnSingleClickListener
            }
            viewModel.setGallery(adapterRecyclerImagesGallery.currentList.map { it.imageToUpdate })
            onFragmentBackPressed()
        }
    }
}

fun PCGalleryAddEventFragment.initAdapter() {
    if (viewModel.getGallery().isNotEmpty()) {
        adapterRecyclerImagesGallery.submitList(
            viewModel.getGallery().map { PCImageToUploadItemModel(imageToUpdate = it) })
        if (viewModel.getGallery().size == 1) {
            binding.mRecyclerImages.itemPercent(1.0)
        } else {
            binding.mRecyclerImages.itemPercent(.88)
        }
    } else {
        adapterRecyclerImagesGallery.submitList(arrayListOf(PCImageToUploadItemModel()))
    }
    binding.mRecyclerImages.apply {
        adapter = adapterRecyclerImagesGallery
        addLinearHelper()
        smoothScrollToPosition(0)
        scrollToPositionCentered()
    }

}