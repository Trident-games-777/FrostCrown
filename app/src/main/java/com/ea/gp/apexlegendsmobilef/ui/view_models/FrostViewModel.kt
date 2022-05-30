package com.ea.gp.apexlegendsmobilef.ui.view_models

import android.app.Application
import android.content.Context
import androidx.ads.identifier.AdvertisingIdClient
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.appsflyer.AppsFlyerLib
import com.ea.gp.apexlegendsmobilef.FrostApplication
import com.ea.gp.apexlegendsmobilef.model.data.FullUrl
import com.ea.gp.apexlegendsmobilef.model.repo.FrostRepository
import com.ea.gp.apexlegendsmobilef.utils.AppsFlyerConversionListenerWrapper
import com.facebook.applinks.AppLinkData
import com.onesignal.OneSignal
import kotlinx.coroutines.*
import java.io.IOException
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FrostViewModel(
    application: Application,
    private val repository: FrostRepository
) : AndroidViewModel(application) {

    private val _linkLiveData: MutableLiveData<String> = MutableLiveData()
    val linkLiveData: LiveData<String> = _linkLiveData

    init {
        viewModelScope.launch {
            if (isFirstLaunch()) {
                setFirstLaunch()
                startFetchingData()
            } else {
                _linkLiveData.postValue(repository.getFullUrl()!!.url)
            }
        }
    }

    private suspend fun startFetchingData() {
        OneSignal.initWithContext(getApplication())
        OneSignal.setAppId(readOneSignalAppId())

        coroutineScope {
            val apps = async { getAppsFlyerDataMap() }
            val deep = async { getFacebookLink() }
            val gadid = async { getAdId() }
            val link = createLink(
                deep.await(),
                apps.await(),
                gadid.await(),
                AppsFlyerLib.getInstance().getAppsFlyerUID(getApplication())
            )
            sendTag(deep.await(), apps.await())
            saveLink(link)
            _linkLiveData.postValue(link)
        }
    }

    suspend fun saveLink(link: String) {
        when (repository.getFullUrl()?.saved ?: 0) {
            0 -> repository.saveFullUrl(FullUrl(url = link, saved = 1))
            1 -> repository.saveFullUrl(FullUrl(url = link, saved = 2))
            else -> {}
        }
    }

    private suspend fun getAdId(): String = withContext(Dispatchers.Default) {
        AdvertisingIdClient.getAdvertisingIdInfo(getApplication()).get().id
    }

    private fun createLink(
        deep: String?,
        apps: MutableMap<String, Any>?,
        gadid: String,
        appsUID: String
    ): String = readBaseLink().toUri().buildUpon().apply {
        appendQueryParameter("ll7svNOYr3", "F642SgI22K")
        appendQueryParameter("PNAcAQTiF6", TimeZone.getDefault().id)
        appendQueryParameter("vRoueZR7KX", gadid)
        appendQueryParameter("BionopaNEk", deep)
        appendQueryParameter("iZxdJvLL26", apps?.get("media_source").toString())
        appendQueryParameter("xezHHWG75Z", appsUID)
        appendQueryParameter("Cnu6W3Ce79", apps?.get("adset_id").toString())
        appendQueryParameter("dcnyWMPjgk", apps?.get("campaign_id").toString())
        appendQueryParameter("aVsWlx8YB3", apps?.get("campaign").toString())
        appendQueryParameter("NfSWpir9sY", apps?.get("adset").toString())
        appendQueryParameter("AuTDD5ZL3D", apps?.get("adgroup").toString())
        appendQueryParameter("PdpNC3NRfu", apps?.get("orig_cost").toString())
        appendQueryParameter("p7q0yVmbFs", apps?.get("af_siteid").toString())
    }.toString()

    private fun sendTag(deep: String?, apps: MutableMap<String, Any>?) {
        val campaign = apps?.get("campaign").toString()

        if (campaign == "null" && (deep == "null" || deep == null)) {
            OneSignal.sendTag("key2", "organic")
        } else if (deep != "null") {
            OneSignal.sendTag("key2", deep?.replace("myapp://", "")?.substringBefore("/"))
        } else if (campaign != "null") {
            OneSignal.sendTag("key2", campaign.substringBefore("_"))
        }
    }

    private fun isFirstLaunch(): Boolean {
        val preferences = getApplication<FrostApplication>()
            .getSharedPreferences("Pref", Context.MODE_PRIVATE)
        return preferences.getBoolean("isFirstLaunch", true)
    }

    private fun setFirstLaunch() {
        val preferences = getApplication<FrostApplication>()
            .getSharedPreferences("Pref", Context.MODE_PRIVATE)
        with(preferences.edit()) {
            putBoolean("isFirstLaunch", false)
            apply()
        }
    }

    private fun readAppsFlyerKey(): String = try {
        getApplication<FrostApplication>().assets.open("appsFlyerKey.txt").use {
            val size = it.available()
            val buffer = ByteArray(size)
            it.read(buffer)
            it.close()
            String(buffer)
        }
    } catch (e: IOException) {
        e.printStackTrace()
        ""
    }

    private fun readOneSignalAppId(): String = try {
        getApplication<FrostApplication>().assets.open("oneSignalKey.txt").use {
            val size = it.available()
            val buffer = ByteArray(size)
            it.read(buffer)
            it.close()
            String(buffer)
        }
    } catch (e: IOException) {
        e.printStackTrace()
        ""
    }

    private fun readBaseLink(): String = try {
        getApplication<FrostApplication>().assets.open("baseLink.txt").use {
            val size = it.available()
            val buffer = ByteArray(size)
            it.read(buffer)
            it.close()
            String(buffer)
        }
    } catch (e: IOException) {
        e.printStackTrace()
        ""
    }

    private suspend fun getAppsFlyerDataMap(): MutableMap<String, Any>? = suspendCoroutine { cont ->
        AppsFlyerLib.getInstance().init(
            readAppsFlyerKey(),
            AppsFlyerConversionListenerWrapper { apps -> cont.resume(apps) },
            getApplication()
        )
        AppsFlyerLib.getInstance().start(getApplication())
    }

    private suspend fun getFacebookLink(): String? = suspendCoroutine { cont ->
        AppLinkData.fetchDeferredAppLinkData(getApplication()) {
            cont.resume(it?.targetUri.toString())
        }
    }
}