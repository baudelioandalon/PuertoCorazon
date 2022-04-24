package com.boreal.puertocorazon.addevent.di

import com.boreal.puertocorazon.addevent.viewmodel.AddEventViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val addEventModule = module {


    viewModel {
        AddEventViewModel()
    }
}