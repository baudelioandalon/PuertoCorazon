package com.boreal.puertocorazon.payments.di

import com.boreal.puertocorazon.core.data.datasource.GetPaymentDataSource
import com.boreal.puertocorazon.core.data.datasource.remote.ARemotePaymentDataSource
import com.boreal.puertocorazon.core.domain.PaymentRepository
import com.boreal.puertocorazon.core.repository.payment.DefaultPaymentRepository
import com.boreal.puertocorazon.core.usecase.login.UseCase
import com.boreal.puertocorazon.core.usecase.payment.PaymentUseCase
import com.boreal.puertocorazon.core.viewmodel.PCMainViewModel
import com.boreal.puertocorazon.payments.ui.PCCartShoppingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val paymentModule =  module {

    single<GetPaymentDataSource>(named("ARemotePaymentDataSource")) {
        ARemotePaymentDataSource()
    }
    single<PaymentRepository>(named("DefaultPaymentRepository")) {
        DefaultPaymentRepository(get(named("ARemotePaymentDataSource")))
    }
    single<UseCase<PaymentUseCase.Input, PaymentUseCase.Output>>(named("PaymentUseCase")) {
        PaymentUseCase(get(named("DefaultPaymentRepository")))
    }

    viewModel {
        PCCartShoppingViewModel(
            get(named("PaymentUseCase"))
        )
    }
}