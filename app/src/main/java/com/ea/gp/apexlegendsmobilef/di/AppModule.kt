package com.ea.gp.apexlegendsmobilef.di

import android.app.Application
import androidx.room.Room
import com.ea.gp.apexlegendsmobilef.model.db.FrostDatabase
import com.ea.gp.apexlegendsmobilef.model.repo.FrostRepository
import com.ea.gp.apexlegendsmobilef.model.repo.FrostRepositoryImpl
import com.ea.gp.apexlegendsmobilef.ui.view_models.FrostViewModelFactory
import com.ea.gp.apexlegendsmobilef.utils.Settings
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

private const val DB_NAME = "frost_db.db"

@Module
class AppModule {
    @Singleton
    @Provides
    fun provideFrostRepository(db: FrostDatabase): FrostRepository {
        return FrostRepositoryImpl(database = db)
    }

    @Singleton
    @Provides
    fun provideFrostDatabase(application: Application): FrostDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            FrostDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    fun provideFrostViewModelFactory(
        app: Application,
        repo: FrostRepository
    ): FrostViewModelFactory {
        return FrostViewModelFactory(application = app, repository = repo)
    }

    @Provides
    fun provideSettings(application: Application): Settings = Settings(application)
}