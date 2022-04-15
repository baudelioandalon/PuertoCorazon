package com.boreal.puertocorazon.addevent.ui.packages

import com.boreal.puertocorazon.core.domain.entity.event.PCPackageModel

fun PCPackagesAddEventFragment.initElements() {
    binding.apply {
        initAdapter()
    }
}

fun PCPackagesAddEventFragment.initAdapter() {
    adapterRecyclerPackages.submitList(arrayListOf(PCPackageModel()))
    binding.mRecyclerPackages.adapter = adapterRecyclerPackages
}