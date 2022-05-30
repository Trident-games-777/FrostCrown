package com.ea.gp.apexlegendsmobilef.ui.view_models

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ea.gp.apexlegendsmobilef.model.repo.FrostRepository

class FrostViewModelFactory(
    private val application: Application,
    private val repository: FrostRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FrostViewModel(application, repository) as T
    }

}