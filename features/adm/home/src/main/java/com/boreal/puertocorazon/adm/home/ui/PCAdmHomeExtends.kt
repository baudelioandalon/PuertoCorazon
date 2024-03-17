package com.boreal.puertocorazon.adm.home.ui

import androidx.navigation.fragment.findNavController
import com.boreal.commonutils.extensions.itemPercent
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.ticket.R as uiR

fun PCAdmHomeFragment.initElements() {
    binding.apply {
        recyclerAdmHomeEvents.apply {
            adapter(adapterRecyclerAdmHomeEvent)
            mainViewModel.requestEvents()
        }
        recyclerHomeServices.apply {
            adapter = adapterRecyclerHomeService
            itemPercent(.88)
        }
        mainViewModel.navToTicket = {
            findNavController().navigate(uiR.id.pc_ticket_graph)
        }
    }
}

fun PCAdmHomeFragment.loadRecyclerEvent(response: List<PCEventModel>) {
    if (response.size > 1) binding.recyclerAdmHomeEvents.itemPercent(.88)
    adapterRecyclerAdmHomeEvent.submitList(response)
}