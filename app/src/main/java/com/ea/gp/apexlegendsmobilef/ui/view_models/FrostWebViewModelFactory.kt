package com.ea.gp.apexlegendsmobilef.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ea.gp.apexlegendsmobilef.model.repo.FrostRepository
import javax.inject.Inject

class FrostWebViewModelFactory @Inject constructor(val repository: FrostRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FrostWebViewModel(repository) as T
    }
}