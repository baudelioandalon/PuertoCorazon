package com.boreal.puertocorazon.login.di


import com.boreal.puertocorazon.core.data.datasource.GetAuthUserDataSource
import com.boreal.puertocorazon.core.data.datasource.local.ALocalAuthDataSource
import com.boreal.puertocorazon.core.domain.AuthRepository
import com.boreal.puertocorazon.core.repository.login.DefaultAuthRepository
import com.boreal.puertocorazon.core.usecase.AuthUseCase
import com.boreal.puertocorazon.core.usecase.EmptyIn
import com.boreal.puertocorazon.core.usecase.UseCase
import com.boreal.puertocorazon.login.data.datasource.GetLoginDataSource
import com.boreal.puertocorazon.login.data.datasource.remote.ARemoteLoginDataSource
import com.boreal.puertocorazon.login.data.repository.DefaultLoginGoogleRepository
import com.boreal.puertocorazon.login.data.repository.DefaultLoginNormalRepository
import com.boreal.puertocorazon.login.domain.LoginGoogleRepository
import com.boreal.puertocorazon.login.domain.LoginNormalRepository
import com.boreal.puertocorazon.login.usecase.LoginGoogleUseCase
import com.boreal.puertocorazon.login.usecase.LoginNormalUseCase
import com.boreal.puertocorazon.login.viewmodel.ALoginViewModel
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
    single<LoginNormalRepository>(named("DefaultLoginNormalRepository")) {
        DefaultLoginNormalRepository(
            get(named("ARemoteLoginDataSource"))
        )
    }

    single<LoginGoogleRepository>(named("DefaultLoginGoogleRepository")) {
        DefaultLoginGoogleRepository(
            get(named("ARemoteLoginDataSource"))
        )
    }

    single<AuthRepository>(named("DefaultAuthRepository")) {
        DefaultAuthRepository(
            get(named("ALocalAuthDataSource"))
        )
    }
    single<UseCase<LoginNormalUseCase.Input, LoginNormalUseCase.Output>>(named("LoginNormalUseCase")) {
        LoginNormalUseCase(get(named("DefaultLoginNormalRepository")))
    }

    single<UseCase<LoginGoogleUseCase.Input, LoginGoogleUseCase.Output>>(named("LoginGoogleUseCase")) {
        LoginGoogleUseCase(get(named("DefaultLoginGoogleRepository")))
    }

    single<UseCase<EmptyIn, AuthUseCase.Output>>(named("AuthUseCase")) {
        AuthUseCase(get(named("DefaultAuthRepository")))
    }

    viewModel {
        ALoginViewModel(
            get(qualifier = named("LoginNormalUseCase")),
            get(qualifier = named("LoginGoogleUseCase")),
            get(qualifier = named("AuthUseCase")),
        )
    }

}