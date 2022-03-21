package com.boreal.puertocorazon.core.domain.entity

import androidx.lifecycle.MutableLiveData

data class AFirestoreGetResponse<R, E>(
    var idKey: String = "",
    var secondIdKey: String? = null,
    var collectionPath: String = "",
    var documentPath: String = "",
    val response: R? = null,
    val failure: E? = null,
    val status: AFirestoreStatusRequest = AFirestoreStatusRequest.NONE
)


fun <R, E> MutableLiveData<AFirestoreGetResponse<R, E>>.requestFirestore(
    valueToSearch: String? = null,
    execute: (onChange: AFirestoreGetResponse<R, E>) -> Unit
) {
    with(
        AFirestoreGetResponse(
            value?.idKey ?: "",
            valueToSearch ?: value?.secondIdKey,
            value?.collectionPath ?: "",
            value?.documentPath ?: "",
            value?.response,
            value?.failure,
            AFirestoreStatusRequest.LOADING
        )
    ) {
        postValue(
            this
        )
        execute.invoke(this)
    }

}