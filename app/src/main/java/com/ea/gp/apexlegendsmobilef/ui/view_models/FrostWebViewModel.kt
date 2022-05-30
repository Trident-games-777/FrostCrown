package com.ea.gp.apexlegendsmobilef.ui.view_models

import androidx.lifecycle.ViewModel
import com.ea.gp.apexlegendsmobilef.model.data.FullUrl
import com.ea.gp.apexlegendsmobilef.model.repo.FrostRepository

class FrostWebViewModel(
    private val repository: FrostRepository
) : ViewModel() {

    suspend fun saveLink(link: String) {
        when (repository.getFullUrl()?.saved ?: 0) {
            0 -> repository.saveFullUrl(FullUrl(url = link, saved = 1))
            1 -> repository.saveFullUrl(FullUrl(url = link, saved = 2))
            else -> {}
        }
    }
}