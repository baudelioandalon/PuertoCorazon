package com.boreal.puertocorazon.ui

import com.boreal.puertocorazon.core.viewmodel.PCBaseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val activityModule = module {
    viewModel {
        PCBaseViewModel()
    }
}