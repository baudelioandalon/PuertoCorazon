package com.boreal.puertocorazon.ticket.ui

import androidx.navigation.fragment.findNavController
import com.boreal.puertocorazon.core.domain.entity.payment.PCPackageTicketModel
import java.lang.Exception

fun PCClientTicketFragment.initElements() {
    binding.apply {

        recyclerClientTicketsEvents.apply {
            adapter(adapterRecyclerTicketsEvents)
            mainViewModel.requestTickets()
        }
        mainViewModel.navToHome = {
            try {
                findNavController().popBackStack()
            }catch (e : Exception){

            }
        }
    }
}

fun PCClientTicketFragment.loadRecyclerEvent(response: List<PCPackageTicketModel>) {
    val groupByIdEvent = response.groupBy(PCPackageTicketModel::idEvent)
        .entries.map {
//            it.key + "${it.value}"
            it.value.groupBy(PCPackageTicketModel::idPayment)
                .entries.map { it2 ->
                    it2.key + "${it2.value}"
                }
        }


//    adapterRecyclerTicketsEvents.submitList(groupByIdEvent)
}