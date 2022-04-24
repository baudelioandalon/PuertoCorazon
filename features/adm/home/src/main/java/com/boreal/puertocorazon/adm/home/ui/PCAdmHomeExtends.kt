package com.boreal.puertocorazon.adm.home.ui

import androidx.navigation.fragment.findNavController
import com.boreal.commonutils.extensions.hideView
import com.boreal.commonutils.extensions.itemPercent
import com.boreal.commonutils.extensions.showView
import com.boreal.puertocorazon.adm.home.R
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel

fun PCAdmHomeFragment.initElements() {
    binding.apply {
        findNavController().addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.PCShowEventFragment, R.id.PCBaseAddEventFragment, R.id.PCShowEventFragment -> {
                    bottomMenu.hideView()
                }
                else -> {
                    bottomMenu.showView()
                }
            }
        }

        recyclerAdmHomeEvents.apply {
            adapter(adapterRecyclerAdmHomeEvent)
            viewModel.requestEvents()
        }
        recyclerHomeServices.apply {
            adapter = adapterRecyclerHomeService
            itemPercent(.88)
        }

        btnNewEvent.setOnClickListener {
            findNavController().navigate(R.id.pc_add_event_graph)
        }
        btnNewEvent.performClick()
    }
}

fun PCAdmHomeFragment.loadRecyclerEvent(response: List<PCEventModel>) {
    if (response.size > 1) binding.recyclerAdmHomeEvents.itemPercent(.88)
    adapterRecyclerAdmHomeEvent.submitList(response)
}