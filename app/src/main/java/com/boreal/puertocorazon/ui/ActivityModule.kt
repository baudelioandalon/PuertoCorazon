package com.boreal.puertocorazon.ui

import com.boreal.puertocorazon.core.data.datasource.GetPaymentDataSource
import com.boreal.puertocorazon.core.data.datasource.GetHomeDataSource
import com.boreal.puertocorazon.core.data.datasource.GetTicketDataSource
import com.boreal.puertocorazon.core.data.datasource.remote.ARemotePaymentDataSource
import com.boreal.puertocorazon.core.data.datasource.remote.PCRemoteHomeDataSource
import com.boreal.puertocorazon.core.data.datasource.remote.PCRemoteTicketDataSource
import com.boreal.puertocorazon.core.domain.HomeRepository
import com.boreal.puertocorazon.core.domain.PaymentRepository
import com.boreal.puertocorazon.core.domain.TicketRepository
import com.boreal.puertocorazon.core.repository.home.DefaultHomeRepository
import com.boreal.puertocorazon.core.repository.payment.DefaultPaymentRepository
import com.boreal.puertocorazon.core.repository.ticket.DefaultTicketRepository
import com.boreal.puertocorazon.core.usecase.login.UseCase
import com.boreal.puertocorazon.core.usecase.home.HomeUseCase
import com.boreal.puertocorazon.core.usecase.payment.PaymentUseCase
import com.boreal.puertocorazon.core.usecase.ticket.TicketUseCase
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

    single<GetTicketDataSource>(named("PCRemoteTicketDataSource")) {
        PCRemoteTicketDataSource()
    }

    single<TicketRepository>(named("DefaultTicketRepository")) {
        DefaultTicketRepository(
            get(named("PCRemoteTicketDataSource"))
        )
    }

    single<UseCase<TicketUseCase.Input, TicketUseCase.Output>>(named("TicketUseCase")) {
        TicketUseCase(get(named("DefaultTicketRepository")))
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
            get(named("PaymentUseCase")),
            get(named("TicketUseCase"))
        )
    }
}