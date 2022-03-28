package com.boreal.puertocorazon.application

import android.app.Application
import com.boreal.puertocorazon.adm.home.di.admHomeModule
import com.boreal.puertocorazon.login.di.loginModule
import com.boreal.puertocorazon.ui.activityModule
import org.koin.core.context.startKoin

class PuertoCorazonApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                activityModule,
                loginModule,
                admHomeModule
            )
        }
    }
}