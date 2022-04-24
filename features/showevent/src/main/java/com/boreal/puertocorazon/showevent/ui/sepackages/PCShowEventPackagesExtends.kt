package com.boreal.puertocorazon.showevent.ui.sepackages

import com.boreal.commonutils.extensions.itemPercent
import com.boreal.puertocorazon.core.domain.entity.event.PCPackageModel
import com.boreal.puertocorazon.core.extension.addLinearHelper
import com.boreal.puertocorazon.core.extension.scrollToPositionCentered

fun PCShowEventPackagesFragment.initElements() {
    binding.apply {
        fillData()
    }
}

fun PCShowEventPackagesFragment.fillData() {
    binding.apply {
        mainViewModel.getEventSelected().apply {
            initAdapter(packages)
        }
    }
}

fun PCShowEventPackagesFragment.initAdapter(packageList: List<PCPackageModel>) {
    adapterRecyclerPackages.submitList(packageList)
    binding.apply {
        mRecyclerPackages.apply {
            addLinearHelper()
            adapter = adapterRecyclerPackages
            smoothScrollToPosition(0)
            scrollToPositionCentered()
            itemPercent(.88)
        }
    }

}