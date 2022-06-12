package com.boreal.puertocorazon.core.repository.event

import com.boreal.puertocorazon.core.domain.entity.AFirestoreGetResponse
import com.boreal.puertocorazon.core.domain.entity.AFirestoreRepository
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.convertData
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.core.utils.coreauthentication.await
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUFirestoreErrorEnum
import com.boreal.puertocorazon.core.utils.log
import com.google.firebase.firestore.Source
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class EventDataSource {
    companion object : AFirestoreRepository() {
        suspend fun getEvent(
            idEvent: String,
            collectionPath: String
        ): AFirestoreGetResponse<PCEventModel> =
            with(
                firestoreInstance.collection(collectionPath)
                    .whereEqualTo("eventData.idEvent", idEvent).get(Source.SERVER).await()
            ) {
                try {
                    with(documents.convertData<PCEventModel>("eventData")) {
                        AFirestoreGetResponse(
                            response = this,
                            failure = null,
                            status = AFirestoreStatusRequest.SUCCESS
                        )
                    }
                } catch (exception: Exception) {
                    "Error getting documents. ${validationError(exception.message!!)}".log(this::class.java.simpleName)
                    AFirestoreGetResponse(
                        response = null,
                        failure = validationError(exception.message!!),
                        status = AFirestoreStatusRequest.FAILURE
                    )
                }
            }

        @OptIn(ExperimentalCoroutinesApi::class)
        suspend fun getEventRealTime(
            idEvent: String,
            collectionPath: String
        ): Flow<AFirestoreGetResponse<PCEventModel>> = callbackFlow {
            val response = firestoreInstance.collection(collectionPath)
                .whereEqualTo("eventData.idEvent", idEvent)

            val subscription = response.addSnapshotListener { snapshot, exception ->
                exception?.let {
                    "Error getting documents. ${validationError(exception.message!!)}".log(this::class.java.simpleName)
                    trySend(
                        AFirestoreGetResponse(
                            response = null,
                            failure = validationError(exception.message!!),
                            status = AFirestoreStatusRequest.FAILURE
                        )
                    )
                    cancel(it.message.toString())
                    return@addSnapshotListener
                }

                if (snapshot?.documents != null) {
                    trySend(with(snapshot.documents.convertData<PCEventModel>("eventData")) {
                        AFirestoreGetResponse(
                            response = this,
                            failure = null,
                            status = AFirestoreStatusRequest.SUCCESS
                        )
                    })
                } else {
                    trySend(
                        element = AFirestoreGetResponse(
                            response = null,
                            failure = CUFirestoreErrorEnum.ERROR_NOT_FOUND,
                            status = AFirestoreStatusRequest.FAILURE
                        )
                    )
                }
            }
            awaitClose { subscription.remove() }
        }
    }

}