package com.ea.gp.apexlegendsmobilef.utils

import android.content.Context
import android.provider.Settings
import com.ea.gp.apexlegendsmobilef.R
import java.io.File

class Settings(private val context: Context) {
    fun isRootsOrAdbEnabled(): Boolean {
        return isRootsEnabled() || getAdbEnabled() == "1"
    }

    private fun isRootsEnabled(): Boolean {
        val dirsArray: Array<String> = context.resources.getStringArray(R.array.dirs_array)
        try {
            for (dir in dirsArray) {
                if (File(dir + "su").exists()) return true
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
        return false
    }

    private fun getAdbEnabled(): String {
        return Settings.Global.getString(context.contentResolver, Settings.Global.ADB_ENABLED)
            ?: "null"
    }
}