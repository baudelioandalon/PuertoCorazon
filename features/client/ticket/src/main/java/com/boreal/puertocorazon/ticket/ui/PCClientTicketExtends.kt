package com.boreal.puertocorazon.ticket.ui

import androidx.navigation.fragment.findNavController
import com.boreal.puertocorazon.core.domain.entity.payment.PCPackageTicketModel

fun PCClientTicketFragment.initElements() {
    binding.apply {

        recyclerClientTicketsEvents.apply {
            adapter(adapterRecyclerTicketsEvents)
            mainViewModel.requestTickets()
        }
        mainViewModel.navToHome = {
            findNavController().popBackStack()
        }
    }
}

fun PCClientTicketFragment.loadRecyclerEvent(response: List<PCPackageTicketModel>) {
    adapterRecyclerTicketsEvents.submitList(response)
}