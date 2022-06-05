package com.boreal.puertocorazon.core.data.datasource.remote

import com.boreal.puertocorazon.core.data.datasource.GetTicketDataSource
import com.boreal.puertocorazon.core.domain.entity.AFirestoreGetResponse
import com.boreal.puertocorazon.core.domain.entity.payment.PCPackageTicketModel
import com.boreal.puertocorazon.core.repository.ticket.TicketDataSource
import kotlinx.coroutines.flow.Flow

class PCRemoteTicketDataSource : GetTicketDataSource {

    override suspend fun executeGetTickets(
        idClient: String,
        collectionPath: String
    ): Flow<AFirestoreGetResponse<List<PCPackageTicketModel>>> =
        TicketDataSource.getTicketsByIdClient(idClient, collectionPath)

    override suspend fun executeGetTicketsByEvent(
        idEvent: String,
        collectionPath: String
    ): Flow<AFirestoreGetResponse<List<PCPackageTicketModel>>> =
        TicketDataSource.getTicketsByIdEvent(idEvent, collectionPath)

}
