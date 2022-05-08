package com.boreal.puertocorazon.adm.home.data

import com.boreal.puertocorazon.core.domain.entity.AFirestoreGetResponse
import com.boreal.puertocorazon.core.domain.entity.AFirestoreRepository
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.convertDataToList
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.core.utils.log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class HomeDataSource {
    companion object : AFirestoreRepository() {
        @OptIn(ExperimentalCoroutinesApi::class)
        fun getEvents(
            idKey: String,
            collectionPath: String
        ): Flow<AFirestoreGetResponse<List<PCEventModel>>> = callbackFlow {
            val response = firestoreInstance.collection(collectionPath)
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
                    trySend(with(snapshot.documents.convertDataToList<PCEventModel>(idKey)) {
                        AFirestoreGetResponse(
                            response = this,
                            failure = null,
                            status = AFirestoreStatusRequest.SUCCESS
                        )
                    })
                }
            }
            awaitClose { subscription.remove() }
        }
    }
}