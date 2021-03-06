package com.boreal.puertocorazon.addevent.di

import com.boreal.puertocorazon.addevent.data.datasource.SetEventDataSource
import com.boreal.puertocorazon.addevent.data.datasource.remote.PCRemoteSetEventDataSource
import com.boreal.puertocorazon.addevent.data.repository.DefaultAddEventRepository
import com.boreal.puertocorazon.addevent.domain.AddEventRepository
import com.boreal.puertocorazon.addevent.usecase.AddEventUseCase
import com.boreal.puertocorazon.addevent.viewmodel.AddEventViewModel
import com.boreal.puertocorazon.core.usecase.login.UseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val addEventModule = module {

    single<SetEventDataSource>(named("PCRemoteAddEventDataSource")) {
        PCRemoteSetEventDataSource()
    }

    single<AddEventRepository>(named("DefaultAddEventRepository")) {
        DefaultAddEventRepository(
            get(named("PCRemoteAddEventDataSource"))
        )
    }
    single<UseCase<AddEventUseCase.Input, AddEventUseCase.Output>>(named("AddEventUseCase")) {
        AddEventUseCase(get(named("DefaultAddEventRepository")))
    }

    viewModel {
        AddEventViewModel(
            get(qualifier = named("AddEventUseCase"))
        )
    }
}