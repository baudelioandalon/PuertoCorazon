package com.boreal.puertocorazon.ticket.ui.showtickets

import com.boreal.puertocorazon.core.domain.entity.payment.PCPackageTicketModel

fun PCTicketFragment.initElements() {
    binding.apply {
        recyclerClientTicketsEvents.apply {
            adapter(adapterRecyclerTicketsEvents)
            mainViewModel.requestTicketsByClient()
        }
    }
}

fun PCTicketFragment.loadRecyclerEvent(response: List<PCPackageTicketModel>) {
    val groupByIdPackage = response.groupBy(PCPackageTicketModel::idPackage)
        .entries.mapIndexed { index, (_, itemList) ->
            with(itemList) {
                PCPackageTicketModel(
                    idPackage = this[0].idPackage,
                    idTicket = this[0].idTicket,
                    idPayment = this[0].idPayment,
                    idClient = this[0].idClient,
                    idEvent = this[0].idEvent,
                    attendedTime = this[0].attendedTime,
                    payedDate = this[0].payedDate,
                    countAdult = if (!this[0].isPackage && this[0].countAdult > 0 && size > 1) size.toLong() else this[0].countAdult,
                    countChild = if (!this[0].isPackage && this[0].countChild > 0 && size > 1) size.toLong() else this[0].countChild,
                    priceItem = this[0].priceItem,
                    nameEvent = this[0].nameEvent,
                    imageEvent = this[0].imageEvent,
                    namePackage = this[0].namePackage,
                    countItem = if (this[0].isPackage) size else 1,
                    isPackage = this[0].isPackage
                )
            }
        }

    adapterRecyclerTicketsEvents.submitList(groupByIdPackage)
}