package com.boreal.puertocorazon.adm.checking.di

import com.boreal.puertocorazon.adm.checking.domain.datasource.UpdateCheckingDataSource
import com.boreal.puertocorazon.adm.checking.domain.datasource.remote.PCRemoteUpdateCheckingSource
import com.boreal.puertocorazon.adm.checking.domain.repository.CheckingRepository
import com.boreal.puertocorazon.adm.checking.domain.repository.DefaultUpdateCheckingRepository
import com.boreal.puertocorazon.adm.checking.usecase.CheckingUseCase
import com.boreal.puertocorazon.adm.checking.viewmodel.PCCheckingViewModel
import com.boreal.puertocorazon.core.usecase.login.UseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val checkingModule = module {

    single<UpdateCheckingDataSource>(named("PCRemoteUpdateCheckingSource")) {
        PCRemoteUpdateCheckingSource()
    }

    single<CheckingRepository>(named("DefaultUpdateCheckingRepository")) {
        DefaultUpdateCheckingRepository(
            get(named("PCRemoteUpdateCheckingSource"))
        )
    }

    single<UseCase<CheckingUseCase.Input, CheckingUseCase.Output>>(named("CheckingUseCase")) {
        CheckingUseCase(get(named("DefaultUpdateCheckingRepository")))
    }

    viewModel {
        PCCheckingViewModel(get(named("CheckingUseCase")))
    }
}