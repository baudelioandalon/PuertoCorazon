package com.boreal.puertocorazon.application

import android.app.Application
import com.boreal.puertocorazon.login.di.loginModule
import org.koin.core.context.startKoin

class PuertoCorazonApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                loginModule
            )
        }
    }
}