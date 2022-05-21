package com.boreal.puertocorazon.core.usecase.ticket

import com.boreal.puertocorazon.core.domain.TicketRepository
import com.boreal.puertocorazon.core.domain.entity.AFirestoreGetResponse
import com.boreal.puertocorazon.core.domain.entity.payment.PCPackageTicketModel
import com.boreal.puertocorazon.core.usecase.login.In
import com.boreal.puertocorazon.core.usecase.login.Out
import com.boreal.puertocorazon.core.usecase.login.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class TicketUseCase(private val ticketRepository: TicketRepository) :
    UseCase<TicketUseCase.Input, TicketUseCase.Output> {

    class Input(val email: String,val collectionPath: String) : In()
    inner class Output(val response: AFirestoreGetResponse<List<PCPackageTicketModel>>) : Out()

    override suspend fun execute(input: Input): Flow<Output> {
        return ticketRepository.executeGetTickets(input.email,input.collectionPath)
            .flowOn(Dispatchers.IO).map {
                Output(it)
            }
    }

}