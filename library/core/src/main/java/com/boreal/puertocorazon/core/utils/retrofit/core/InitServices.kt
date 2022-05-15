package com.boreal.puertocorazon.core.utils.retrofit.core

import com.boreal.puertocorazon.core.utils.retrofit.core.retrofit.ApiServices
import com.boreal.puertocorazon.core.utils.retrofit.core.retrofit.RetroClient

class InitServices<R, RQ> {

    fun postExecuteService(request: RQ, endPoint: String) =
        RetroClient.getRestEngine().create(ApiServices::class.java)
            .serviceResponsePostBody(endPoint, (request as Any)) as R

}
