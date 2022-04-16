package com.boreal.puertocorazon.addevent.ui.packages

import androidx.recyclerview.widget.LinearSnapHelper
import com.boreal.puertocorazon.core.domain.entity.event.PCPackageToUploadModel

fun PCPackagesAddEventFragment.initElements() {
    binding.apply {
        initAdapter()
    }
}

fun PCPackagesAddEventFragment.initAdapter() {
    binding.mRecyclerPackages.apply {
        LinearSnapHelper().attachToRecyclerView(this)
        adapter = adapterRecyclerPackages
    }
    adapterRecyclerPackages.submitList(arrayListOf(PCPackageToUploadModel()))

}