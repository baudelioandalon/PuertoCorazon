package com.boreal.puertocorazon.core.domain

import com.boreal.puertocorazon.core.domain.entity.AFirestoreGetResponse
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.core.domain.entity.payment.PCPackageTicketModel
import kotlinx.coroutines.flow.Flow

interface TicketRepository {
    suspend fun executeGetTickets(
        idClient: String = "",
        collectionPath: String = ""
    ): Flow<AFirestoreGetResponse<List<PCPackageTicketModel>>>

    suspend fun executeGetTicketsByEvent(
        idEvent: String = "",
        collectionPath: String = ""
    ): Flow<AFirestoreGetResponse<List<PCPackageTicketModel>>>
}