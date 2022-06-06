package com.boreal.puertocorazon.adm.checking.domain.repository

import com.boreal.puertocorazon.core.domain.entity.AFirestoreSetResponse
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUFirestoreErrorEnum
import kotlinx.coroutines.flow.Flow

interface CheckingRepository {
    suspend fun executeUpdateChecking(
        checkingModel: ArrayList<Map<String, Any>>,
        collectionPath: String = "",
        documentPath: String = ""
    ): Flow<AFirestoreSetResponse<ArrayList<Map<String, Any>>, CUFirestoreErrorEnum>>
}