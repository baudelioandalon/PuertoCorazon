package com.boreal.puertocorazon.adm.home.di

import com.boreal.puertocorazon.adm.home.data.datasource.GetHomeDataSource
import com.boreal.puertocorazon.adm.home.data.datasource.remote.PCRemoteHomeDataSource
import com.boreal.puertocorazon.adm.home.data.repository.DefaultHomeRepository
import com.boreal.puertocorazon.adm.home.domain.HomeRepository
import com.boreal.puertocorazon.adm.home.ui.PCHomeViewModel
import com.boreal.puertocorazon.adm.home.usecase.HomeUseCase
import com.boreal.puertocorazon.core.usecase.AuthUseCase
import com.boreal.puertocorazon.core.usecase.EmptyIn
import com.boreal.puertocorazon.core.usecase.UseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val admHomeModule = module {

    single<GetHomeDataSource>(named("PCRemoteHomeDataSource")) {
        PCRemoteHomeDataSource()
    }

    single<HomeRepository>(named("DefaultHomeRepository")) {
        DefaultHomeRepository(
            get(named("PCRemoteHomeDataSource"))
        )
    }
    single<UseCase<EmptyIn, AuthUseCase.Output>>(named("AuthUseCase")) {
        AuthUseCase(get(named("DefaultAuthRepository")))
    }
    single<UseCase<HomeUseCase.Input, HomeUseCase.Output>>(named("HomeUseCase")) {
        HomeUseCase(get(named("DefaultHomeRepository")))
    }

    viewModel {
        PCHomeViewModel(
            get(named("HomeUseCase")),
            get(named("AuthUseCase"))
        )
    }
}