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
        recyclerAdmHomeEvents.apply {
            adapter(adapterRecyclerAdmHomeEvent)
            mainViewModel.requestEvents()
        }
        recyclerHomeServices.apply {
            adapter = adapterRecyclerHomeService
            itemPercent(.88)
        }
        mainViewModel.navToTicket = {
            findNavController().navigate(R.id.pc_ticket_graph)
        }
    }
}

fun PCAdmHomeFragment.loadRecyclerEvent(response: List<PCEventModel>) {
    if (response.size > 1) binding.recyclerAdmHomeEvents.itemPercent(.88)
    adapterRecyclerAdmHomeEvent.submitList(response)
}