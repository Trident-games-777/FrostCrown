package com.ea.gp.apexlegendsmobilef.di

import android.app.Application
import com.ea.gp.apexlegendsmobilef.ui.screens.FrostLoadingActivity
import com.ea.gp.apexlegendsmobilef.ui.screens.WebFrostActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(frostLoadingActivity: FrostLoadingActivity)
    fun inject(webFrostActivity: WebFrostActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun frostApp(application: Application): Builder

        fun build(): AppComponent
    }

}