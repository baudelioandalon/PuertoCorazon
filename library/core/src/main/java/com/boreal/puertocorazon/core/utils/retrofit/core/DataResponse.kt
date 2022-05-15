package com.boreal.puertocorazon.core.utils.retrofit.core

data class DataResponse<R>(
    val statusRequest: StatusRequestEnum = StatusRequestEnum.NONE,
    val successData: R? = null,
    val errorModel: R? = null,
    var errorData: String? = null
)
