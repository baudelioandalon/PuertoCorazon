package com.boreal.puertocorazon.adm.home.data.repository

import com.boreal.puertocorazon.adm.home.data.datasource.GetHomeDataSource
import com.boreal.puertocorazon.adm.home.domain.HomeRepository
import com.boreal.puertocorazon.core.domain.entity.AFirestoreGetResponse
import com.boreal.puertocorazon.core.domain.entity.AFirestoreRepository
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class DefaultHomeRepository(
    private val getHomeDataSource: GetHomeDataSource
) : HomeRepository {

    override suspend fun executeGetEvents(
        idKey: String,
        collectionPath: String
    ): Flow<AFirestoreGetResponse<List<PCEventModel>>> = flow {
        emit(getHomeDataSource.executeGetEvents(idKey, collectionPath))
    }.catch { cause ->
        emit(
            AFirestoreGetResponse(
                response = null,
                failure = AFirestoreRepository.errorResponse(cause),
                status = AFirestoreStatusRequest.FAILURE
            )
        )
    }
//    ): Flow<AFirestoreGetResponse<List<PCEventModel>>> = flow {
//        emit(getHomeDataSource.executeGetEvents(idKey, collectionPath))
//    }.handleErrors()
}

//fun <T> Flow<T>.handleErrors(): Flow<AFirestoreGetResponse<T>> = flow {
//    try {
//        collect { value ->
//            emit(
//                AFirestoreGetResponse(
//                    response = value,
//                    failure = null,
//                    status = AFirestoreStatusRequest.SUCCESS
//                )
//            )
//        }
//    } catch (exception: Exception) {
//        Log.e(
//            this::class.java.simpleName,
//            "Error getting documents. ${AFirestoreRepository.validationError(exception.message!!)}"
//        )
//        emit(
//            AFirestoreGetResponse(
//                response = null,
//                failure = AFirestoreRepository.validationError(exception.message!!),
//                status = AFirestoreStatusRequest.FAILURE
//            )
//        )
//    }
//}