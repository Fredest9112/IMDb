package com.globant.imdb.model.splashFragment

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.globant.imdb.data.Constants.IS_USER_LOGGED
import com.globant.imdb.data.Constants.LOGIN_PREFERENCES
import com.globant.imdb.repo.DatabaseRepo
import com.globant.imdb.utils.DatabaseResult

class SplashViewModel(private val databaseRepo: DatabaseRepo) : ViewModel() {

    private var _isDataSaved = MutableLiveData<DatabaseResult>()
    val isDataSaved: LiveData<DatabaseResult> = _isDataSaved

    private lateinit var loginPreferences: SharedPreferences

    init {
        _isDataSaved.value = DatabaseResult.DatabaseError("",false)
    }

    suspend fun deleteMoviesOnDB() {
        databaseRepo.deleteAllMovies()
    }

    suspend fun saveTopRatedMoviesToDB() {
        _isDataSaved.value = databaseRepo.insertMoviesOnDB()
    }

    fun initSharedPreferences(activity: Activity) {
        loginPreferences = activity.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun checkLoginPreferences(): Boolean {
        return loginPreferences.getBoolean(IS_USER_LOGGED, false)
    }
}