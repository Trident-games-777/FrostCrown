package com.ea.gp.apexlegendsmobilef.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ea.gp.apexlegendsmobilef.model.data.LAUNCH_NAME
import com.ea.gp.apexlegendsmobilef.model.data.Launch

@Dao
interface LaunchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setFirstLaunch(launch: Launch)

    @Query("SELECT * FROM $LAUNCH_NAME")
    suspend fun isFirstLaunch(): Launch?
}