package com.globant.imdb.model.splashFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.globant.imdb.repo.DatabaseRepo

class SplashViewModel(private val databaseRepo: DatabaseRepo): ViewModel() {

    private var _isDataSaved = MutableLiveData<Boolean>()
    val isDataSaved: LiveData<Boolean> = _isDataSaved

    init {
        _isDataSaved.value = false
    }

    suspend fun saveTopRatedMoviesToDB() {
        _isDataSaved.value = databaseRepo.insertMoviesOnDB()
    }
}