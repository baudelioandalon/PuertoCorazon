package com.boreal.puertocorazon.core.data.datasource.remote

import com.boreal.puertocorazon.core.data.datasource.GetPaymentDataSource
import com.boreal.puertocorazon.core.domain.entity.payment.PCPaymentRequest
import com.boreal.puertocorazon.core.domain.entity.payment.PCPaymentResponse
import com.boreal.puertocorazon.core.utils.retrofit.core.DataResponse

class ARemotePaymentDataSource : GetPaymentDataSource {

    override suspend fun getPayment(
        request: PCPaymentRequest
    ): DataResponse<PCPaymentResponse> {
        return PaymentDataSource.getData(request).also {
            it.errorData = it.errorData ?: it.errorModel?.message ?: "Por favor intente mas tarde."
        }
    }
}