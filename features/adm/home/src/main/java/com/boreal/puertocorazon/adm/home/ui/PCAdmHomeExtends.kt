package com.boreal.puertocorazon.adm.home.ui

import androidx.navigation.fragment.findNavController
import com.boreal.commonutils.extensions.hideView
import com.boreal.commonutils.extensions.itemPercent
import com.boreal.commonutils.extensions.onClick
import com.boreal.commonutils.extensions.showView
import com.boreal.puertocorazon.adm.home.R
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel

fun PCAdmHomeFragment.initElements() {
    binding.apply {
        userProfile = mainViewModel.getImageProfile()
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

        btnNotifications.onClick {
            mainViewModel.signOutUser()
        }

        recyclerAdmHomeEvents.apply {
            adapter(adapterRecyclerAdmHomeEvent)
            mainViewModel.requestEvents()
        }
        recyclerHomeServices.apply {
            adapter = adapterRecyclerHomeService
            itemPercent(.88)
        }

        btnNewEvent.setOnClickListener {
            findNavController().navigate(R.id.pc_add_event_graph)
        }
    }
}

fun PCAdmHomeFragment.loadRecyclerEvent(response: List<PCEventModel>) {
    if (response.size > 1) binding.recyclerAdmHomeEvents.itemPercent(.88)
    adapterRecyclerAdmHomeEvent.submitList(response)
}