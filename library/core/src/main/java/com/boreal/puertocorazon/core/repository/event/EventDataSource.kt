package com.boreal.puertocorazon.core.repository.event

import com.boreal.puertocorazon.core.domain.entity.AFirestoreGetResponse
import com.boreal.puertocorazon.core.domain.entity.AFirestoreRepository
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.convertData
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.core.utils.coreauthentication.await
import com.boreal.puertocorazon.core.utils.log
import com.google.firebase.firestore.Source

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
    }

}