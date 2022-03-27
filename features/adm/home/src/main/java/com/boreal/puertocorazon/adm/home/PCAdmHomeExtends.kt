package com.boreal.puertocorazon.adm.home

import androidx.navigation.fragment.findNavController
import com.boreal.commonutils.extensions.hideView
import com.boreal.commonutils.extensions.itemPercent
import com.boreal.commonutils.extensions.showView

fun PCAdmHomeFragment.initElements() {
    binding.apply {
        findNavController().addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.PCShowEventFragment -> {
                    bottomMenu.hideView()
                }
                else -> {
                    bottomMenu.showView()
                }
            }
        }

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