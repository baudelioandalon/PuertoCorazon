package com.boreal.puertocorazon.core.repository.ticket

import com.boreal.puertocorazon.core.data.datasource.GetTicketDataSource
import com.boreal.puertocorazon.core.domain.TicketRepository
import com.boreal.puertocorazon.core.domain.entity.AFirestoreGetResponse
import com.boreal.puertocorazon.core.domain.entity.payment.PCPackageTicketModel
import kotlinx.coroutines.flow.Flow

class DefaultTicketRepository(
    private val getTicketDataSource: GetTicketDataSource
) : TicketRepository {

    override suspend fun executeGetTickets(
        idClient: String,
        collectionPath: String
    ): Flow<AFirestoreGetResponse<List<PCPackageTicketModel>>> =
        getTicketDataSource.executeGetTickets(idClient, collectionPath)

    override suspend fun executeGetTicketsByEvent(
        idEvent: String,
        collectionPath: String
    ): Flow<AFirestoreGetResponse<List<PCPackageTicketModel>>> =
        getTicketDataSource.executeGetTicketsByEvent(idEvent, collectionPath)
}