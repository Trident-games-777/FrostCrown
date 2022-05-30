package com.ea.gp.apexlegendsmobilef

import android.app.Application
import android.content.Context
import com.ea.gp.apexlegendsmobilef.di.AppComponent
import com.ea.gp.apexlegendsmobilef.di.DaggerAppComponent

val Context.appComponent: AppComponent
    get() {
        return when (this) {
            is FrostApplication -> appComponent
            else -> this.applicationContext.appComponent
        }
    }

class FrostApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .frostApp(this)
            .build()
    }
}