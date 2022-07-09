package com.boreal.puertocorazon.home

import androidx.navigation.fragment.findNavController
import com.boreal.commonutils.extensions.itemPercent
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel

fun PCClientHomeFragment.initElements() {
    binding.apply {

        recyclerClientHomeEvents.apply {
            adapter(adapterRecyclerHomeEvent)
            mainViewModel.requestEvents()
        }
        recyclerHomeServices.apply {
            adapter = adapterRecyclerHomeService
            itemPercent(.88)
        }
    }
    mainViewModel.navToTicket = {
        findNavController().navigate(R.id.pc_ticket_graph)
    }

    mainViewModel.navToMap = {
        findNavController().navigate(R.id.pc_map_graph)
    }

    mainViewModel.navToHome = {
        findNavController().navigate(R.id.pc_client_home_graph)
    }
}

fun PCClientHomeFragment.loadRecyclerEvent(response: List<PCEventModel>) {
    if (response.size > 1) binding.recyclerClientHomeEvents.itemPercent(.88)
    adapterRecyclerHomeEvent.submitList(response)
}