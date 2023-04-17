package com.globant.imdb.model.splashFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.globant.imdb.repo.DatabaseRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Suppress("UNCHECKED_CAST")
class SplashViewModelFactory @Inject constructor(private val databaseRepo: DatabaseRepo) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SplashViewModel(databaseRepo) as T
    }
}