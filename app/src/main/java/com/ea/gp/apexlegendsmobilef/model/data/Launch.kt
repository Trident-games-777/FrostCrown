package com.ea.gp.apexlegendsmobilef.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val LAUNCH_NAME = "launch_table"

@Entity(tableName = LAUNCH_NAME)
data class Launch(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    @ColumnInfo(name = "is_first_launch")
    val name: String = "Is first launch",
)
