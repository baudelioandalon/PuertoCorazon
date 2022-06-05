package com.boreal.puertocorazon.core.data.datasource

import com.boreal.puertocorazon.core.domain.entity.AFirestoreGetResponse
import com.boreal.puertocorazon.core.domain.entity.payment.PCPackageTicketModel
import kotlinx.coroutines.flow.Flow

interface GetTicketDataSource {
    suspend fun executeGetTickets(
        idClient: String = "",
        collectionPath: String = "",
    ): Flow<AFirestoreGetResponse<List<PCPackageTicketModel>>>

    suspend fun executeGetTicketsByEvent(
        idEvent: String = "",
        collectionPath: String = "",
    ): Flow<AFirestoreGetResponse<List<PCPackageTicketModel>>>
}