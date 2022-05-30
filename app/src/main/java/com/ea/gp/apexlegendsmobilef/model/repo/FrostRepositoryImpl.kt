package com.ea.gp.apexlegendsmobilef.model.repo

import com.ea.gp.apexlegendsmobilef.model.data.FullUrl
import com.ea.gp.apexlegendsmobilef.model.data.Launch
import com.ea.gp.apexlegendsmobilef.model.db.FrostDatabase
import javax.inject.Inject

class FrostRepositoryImpl @Inject constructor(
    private val database: FrostDatabase
) : FrostRepository {

    override suspend fun saveFullUrl(fullUrl: FullUrl) {
        database.urlDao().upsertFullUrl(fullUrl)
    }

    override suspend fun getFullUrl(): FullUrl? {
        return database.urlDao().getFullUrl()
    }

    override suspend fun setFirstLaunch() {
        database.launchDao().setFirstLaunch(Launch())
    }

    override suspend fun isFirstLaunch(): Launch? {
        return database.launchDao().isFirstLaunch()
    }
}