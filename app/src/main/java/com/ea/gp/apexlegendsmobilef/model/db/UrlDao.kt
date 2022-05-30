package com.ea.gp.apexlegendsmobilef.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ea.gp.apexlegendsmobilef.model.data.FullUrl
import com.ea.gp.apexlegendsmobilef.model.data.TABLE_NAME

@Dao
interface UrlDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertFullUrl(fullUrl: FullUrl)

    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun getFullUrl(): FullUrl?
}