package com.ea.gp.apexlegendsmobilef.model.repo

import com.ea.gp.apexlegendsmobilef.model.data.FullUrl
import com.ea.gp.apexlegendsmobilef.model.data.Launch

interface FrostRepository {
    suspend fun saveFullUrl(fullUrl: FullUrl)
    suspend fun getFullUrl(): FullUrl?
    suspend fun setFirstLaunch()
    suspend fun isFirstLaunch(): Launch?
}