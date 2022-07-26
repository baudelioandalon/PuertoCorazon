package com.boreal.puertocorazon.core.repository.payment

import com.boreal.puertocorazon.core.data.datasource.GetPaymentDataSource
import com.boreal.puertocorazon.core.domain.PaymentRepository
import com.boreal.puertocorazon.core.domain.entity.payment.PCPaymentRequest
import com.boreal.puertocorazon.core.domain.entity.payment.PCPaymentResponse
import com.boreal.puertocorazon.core.utils.retrofit.core.DataResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultPaymentRepository(
    private val getPaymentDataSource: GetPaymentDataSource
) : PaymentRepository {

    override suspend fun getPayment(
        request: PCPaymentRequest
    ): Flow<DataResponse<PCPaymentResponse>> =
        flow {
            emit(getPaymentDataSource.getPayment(request))
        }

}