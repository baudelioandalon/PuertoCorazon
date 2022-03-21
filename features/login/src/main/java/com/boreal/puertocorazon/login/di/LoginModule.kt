package com.boreal.puertocorazon.login.di

import com.boreal.puertocorazon.core.usecase.UseCase
import com.boreal.puertocorazon.login.ui.login.ALoginViewModel
import com.boreal.puertocorazon.login.ui.login.data.datasource.GetLoginDataSource
import com.boreal.puertocorazon.login.data.datasource.remote.ARemoteLoginDataSource
import com.boreal.puertocorazon.login.data.repository.DefaultLoginRepository
import com.boreal.puertocorazon.login.ui.login.domain.LoginRepository
import com.boreal.puertocorazon.login.ui.login.usecase.LoginUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val loginModule = module {
    single<GetLoginDataSource>(named("ARemoteLoginDataSource")) {
        ARemoteLoginDataSource()
    }
    single<LoginRepository>(named("DefaultLoginRepository")) {
        DefaultLoginRepository(get(named("ARemoteLoginDataSource")))
    }
    single<UseCase<LoginUseCase.Input, LoginUseCase.Output>>(named("LoginUseCase")) {
        LoginUseCase(get(named("DefaultLoginRepository")))
    }

    viewModel {
        ALoginViewModel(
            get(qualifier = named("LoginUseCase"))
        )
    }

}