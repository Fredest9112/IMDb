package com.globant.imdb.model.splashFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.globant.imdb.repo.DatabaseRepo

@Suppress("UNCHECKED_CAST")
class SplashViewModelFactory(private val databaseRepo: DatabaseRepo): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SplashViewModel(databaseRepo) as T
    }
}