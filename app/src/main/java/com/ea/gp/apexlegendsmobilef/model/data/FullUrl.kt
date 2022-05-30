package com.ea.gp.apexlegendsmobilef.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val TABLE_NAME = "url_table"

@Entity(tableName = TABLE_NAME)
data class FullUrl(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    @ColumnInfo(name = "url_column")
    val url: String,
    @ColumnInfo(name = "saved_count_column")
    val saved: Int
)