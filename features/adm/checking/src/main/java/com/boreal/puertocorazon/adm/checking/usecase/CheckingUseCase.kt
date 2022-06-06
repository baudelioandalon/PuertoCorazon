package com.boreal.puertocorazon.adm.checking.usecase

import com.boreal.puertocorazon.adm.checking.domain.repository.CheckingRepository
import com.boreal.puertocorazon.core.domain.entity.AFirestoreGetResponse
import com.boreal.puertocorazon.core.domain.entity.AFirestoreSetResponse
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.core.usecase.login.In
import com.boreal.puertocorazon.core.usecase.login.Out
import com.boreal.puertocorazon.core.usecase.login.UseCase
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUFirestoreErrorEnum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class CheckingUseCase(private val checkingRepository: CheckingRepository) :
    UseCase<CheckingUseCase.Input, CheckingUseCase.Output> {

    class Input(
        val checkingModel: ArrayList<Map<String, Any>>,
        val collectionPath: String,
        val documentPath: String = ""
    ) : In()

    inner class Output(val response: AFirestoreSetResponse<ArrayList<Map<String, Any>>, CUFirestoreErrorEnum>) :
        Out()

    override suspend fun execute(input: Input): Flow<Output> {
        return checkingRepository.executeUpdateChecking(
            input.checkingModel,
            input.collectionPath,
            input.documentPath
        )
            .flowOn(Dispatchers.IO).map {
                Output(it)
            }
    }

}