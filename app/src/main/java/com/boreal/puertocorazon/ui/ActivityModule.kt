package com.boreal.puertocorazon.ui

import com.boreal.puertocorazon.core.data.datasource.remote.PCRemoteHomeDataSource
import com.boreal.puertocorazon.core.data.datasource.GetHomeDataSource
import com.boreal.puertocorazon.core.domain.HomeRepository
import com.boreal.puertocorazon.core.repository.home.DefaultHomeRepository
import com.boreal.puertocorazon.core.usecase.UseCase
import com.boreal.puertocorazon.core.usecase.home.HomeUseCase
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
    viewModel {
        PCMainViewModel(
            get(named("HomeUseCase"))
        )
    }
}