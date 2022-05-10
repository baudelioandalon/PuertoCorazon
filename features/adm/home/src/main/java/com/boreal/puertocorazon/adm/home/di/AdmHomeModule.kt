package com.boreal.puertocorazon.adm.home.di

import com.boreal.puertocorazon.adm.home.ui.PCHomeViewModel
import com.boreal.puertocorazon.core.usecase.AuthUseCase
import com.boreal.puertocorazon.core.usecase.EmptyIn
import com.boreal.puertocorazon.core.usecase.UseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val admHomeModule = module {

    single<UseCase<EmptyIn, AuthUseCase.Output>>(named("AuthUseCase")) {
        AuthUseCase(get(named("DefaultAuthRepository")))
    }

    viewModel {
        PCHomeViewModel(
            get(named("AuthUseCase"))
        )
    }
}