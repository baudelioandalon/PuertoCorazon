package com.boreal.puertocorazon.generic.ui.fragments.home

import com.boreal.commonutils.extensions.itemPercent


fun PCHomeFragment.initElements() {
    mBinding.apply {
        recyclerHomeEvents.apply {
            adapter = adapterRecyclerHomeEvent
            itemPercent(.88)
            adapterRecyclerHomeEvent.submitList(arrayListOf("texto", "texto"))
        }
        recyclerHomeServices.apply {
            adapter = adapterRecyclerHomeService
            itemPercent(.88)
            adapterRecyclerHomeService.submitList(arrayListOf("texto", "texto"))
        }
    }
}