package com.ea.gp.apexlegendsmobilef.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ea.gp.apexlegendsmobilef.model.data.FullUrl
import com.ea.gp.apexlegendsmobilef.model.data.Launch

@Database(entities = [FullUrl::class, Launch::class], version = 1)
abstract class FrostDatabase : RoomDatabase() {
    abstract fun urlDao(): UrlDao
    abstract fun launchDao(): LaunchDao
}