package com.boreal.puertocorazon.login.di


import com.boreal.puertocorazon.core.usecase.EmptyIn
import com.boreal.puertocorazon.core.usecase.UseCase
import com.boreal.puertocorazon.core.data.datasource.GetAuthUserDataSource
import com.boreal.puertocorazon.login.data.datasource.GetLoginDataSource
import com.boreal.puertocorazon.core.data.datasource.local.ALocalAuthDataSource
import com.boreal.puertocorazon.login.data.datasource.remote.ARemoteLoginDataSource
import com.boreal.puertocorazon.core.repository.DefaultAuthRepository
import com.boreal.puertocorazon.login.data.repository.DefaultLoginRepository
import com.boreal.puertocorazon.core.domain.AuthRepository
import com.boreal.puertocorazon.login.domain.LoginRepository
import com.boreal.puertocorazon.login.ui.ALoginViewModel
import com.boreal.puertocorazon.core.usecase.AuthUseCase
import com.boreal.puertocorazon.login.usecase.LoginUseCase

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val loginModule = module {
    single<GetLoginDataSource>(named("ARemoteLoginDataSource")) {
        ARemoteLoginDataSource()
    }
    single<GetAuthUserDataSource>(named("ALocalAuthDataSource")) {
        ALocalAuthDataSource()
    }
    single<LoginRepository>(named("DefaultLoginRepository")) {
        DefaultLoginRepository(
            get(named("ARemoteLoginDataSource"))
        )
    }

    single<AuthRepository>(named("DefaultAuthRepository")) {
        DefaultAuthRepository(
            get(named("ALocalAuthDataSource"))
        )
    }
    single<UseCase<LoginUseCase.Input, LoginUseCase.Output>>(named("LoginUseCase")) {
        LoginUseCase(get(named("DefaultLoginRepository")))
    }

    single<UseCase<EmptyIn, AuthUseCase.Output>>(named("AuthUseCase")) {
        AuthUseCase(get(named("DefaultAuthRepository")))
    }

    viewModel {
        ALoginViewModel(
            get(qualifier = named("LoginUseCase")),
            get(qualifier = named("AuthUseCase")),
        )
    }

}