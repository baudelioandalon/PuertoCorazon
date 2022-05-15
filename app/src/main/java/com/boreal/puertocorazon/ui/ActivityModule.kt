package com.boreal.puertocorazon.ui

import com.boreal.puertocorazon.core.data.GetPaymentDataSource
import com.boreal.puertocorazon.core.data.datasource.GetHomeDataSource
import com.boreal.puertocorazon.core.data.datasource.remote.ARemotePaymentDataSource
import com.boreal.puertocorazon.core.data.datasource.remote.PCRemoteHomeDataSource
import com.boreal.puertocorazon.core.domain.HomeRepository
import com.boreal.puertocorazon.core.domain.PaymentRepository
import com.boreal.puertocorazon.core.repository.home.DefaultHomeRepository
import com.boreal.puertocorazon.core.repository.payment.DefaultPaymentRepository
import com.boreal.puertocorazon.core.usecase.UseCase
import com.boreal.puertocorazon.core.usecase.home.HomeUseCase
import com.boreal.puertocorazon.core.usecase.payment.PaymentUseCase
import com.boreal.puertocorazon.core.viewmodel.PCMainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val activityModule = module {

    single<GetHomeDataSource>(named("PCRemoteHomeDataSource")) {
        PCRemoteHomeDataSource()
    }
    single<HomeRepository>(named("DefaultHomeRepository")) {
        DefaultHomeRepository(
            get(named("PCRemoteHomeDataSource"))
        )
    }
    single<UseCase<HomeUseCase.Input, HomeUseCase.Output>>(named("HomeUseCase")) {
        HomeUseCase(get(named("DefaultHomeRepository")))
    }

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
        PCMainViewModel(
            get(named("HomeUseCase")),
            get(named("PaymentUseCase"))
        )
    }
}