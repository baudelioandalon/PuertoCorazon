package com.boreal.puertocorazon.application

import android.app.Application
import com.boreal.puertocorazon.addevent.di.addEventModule
import com.boreal.puertocorazon.adm.checking.di.checkingModule
import com.boreal.puertocorazon.adm.home.di.admHomeModule
import com.boreal.puertocorazon.login.di.loginModule
import com.boreal.puertocorazon.payments.di.paymentModule
import com.boreal.puertocorazon.di.activityModule
import org.koin.core.context.startKoin

class PuertoCorazonApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                activityModule,
                loginModule,
                admHomeModule,
                addEventModule,
                paymentModule,
                checkingModule
            )
        }
    }
}