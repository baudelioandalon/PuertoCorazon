package com.boreal.puertocorazon.client.home.ui

import androidx.navigation.fragment.findNavController
import com.boreal.commonutils.extensions.hideView
import com.boreal.commonutils.extensions.itemPercent
import com.boreal.commonutils.extensions.onClick
import com.boreal.commonutils.extensions.showView
import com.boreal.puertocorazon.client.home.R
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel

fun PCClientHomeFragment.initElements() {
    binding.apply {
        userProfile = mainViewModel.getImageProfile()
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

        btnNotifications.onClick {
            mainViewModel.signOutUser()
        }

        recyclerClientHomeEvents.apply {
            adapter(adapterRecyclerHomeEvent)
            mainViewModel.requestEvents(mainViewModel.getEmailUser())
        }
        recyclerHomeServices.apply {
            adapter = adapterRecyclerHomeService
            itemPercent(.88)
        }
    }
}

fun PCClientHomeFragment.loadRecyclerEvent(response: List<PCEventModel>) {
    if (response.size > 1) binding.recyclerClientHomeEvents.itemPercent(.88)
    adapterRecyclerHomeEvent.submitList(response)
}