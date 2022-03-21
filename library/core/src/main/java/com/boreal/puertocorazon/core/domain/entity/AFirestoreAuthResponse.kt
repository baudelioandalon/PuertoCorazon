package com.boreal.puertocorazon.core.domain.entity

import com.boreal.altemis.core.domain.entity.auth.AAuthenticationType

data class AFirestoreAuthResponse<M, R, E>(
    val authModel: M,
    val response: R? = null,
    val failure: E? = null,
    val authType: AAuthenticationType = AAuthenticationType.EMAIL,
    val status: AFirestoreStatusRequest = AFirestoreStatusRequest.NONE
)