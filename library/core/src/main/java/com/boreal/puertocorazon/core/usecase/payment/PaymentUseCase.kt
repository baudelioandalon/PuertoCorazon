package com.boreal.puertocorazon.core.usecase.payment

import com.boreal.puertocorazon.core.domain.PaymentRepository
import com.boreal.puertocorazon.core.domain.entity.payment.PCPaymentRequest
import com.boreal.puertocorazon.core.domain.entity.payment.PCPaymentResponse
import com.boreal.puertocorazon.core.usecase.login.In
import com.boreal.puertocorazon.core.usecase.login.Out
import com.boreal.puertocorazon.core.usecase.login.UseCase
import com.boreal.puertocorazon.core.utils.retrofit.core.DataResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class PaymentUseCase(private val paymentRepository: PaymentRepository) :
    UseCase<PaymentUseCase.Input, PaymentUseCase.Output> {

    class Input(val request: PCPaymentRequest) : In()
    inner class Output(val response: DataResponse<PCPaymentResponse>) : Out()

    override suspend fun execute(input: Input): Flow<Output> {
        return paymentRepository.getPayment(input.request)
            .flowOn(Dispatchers.IO).map {
            Output(it)
        }
    }

}