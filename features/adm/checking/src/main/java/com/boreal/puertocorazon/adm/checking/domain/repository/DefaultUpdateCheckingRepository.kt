package com.boreal.puertocorazon.adm.checking.domain.repository

import com.boreal.puertocorazon.adm.checking.domain.datasource.UpdateCheckingDataSource
import com.boreal.puertocorazon.core.domain.entity.AFirestoreSetResponse
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUFirestoreErrorEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultUpdateCheckingRepository(
    private val updateCheckingDataSource: UpdateCheckingDataSource
) : CheckingRepository {

    override suspend fun executeUpdateChecking(
        checkingModel: ArrayList<Map<String, Any>>,
        collectionPath: String,
        documentPath: String
    ): Flow<AFirestoreSetResponse<ArrayList<Map<String, Any>>, CUFirestoreErrorEnum>> = flow {
        emit(
            updateCheckingDataSource.executeUpdateChecking(
                checkingModel,
                collectionPath,
                documentPath
            )
        )
    }

}