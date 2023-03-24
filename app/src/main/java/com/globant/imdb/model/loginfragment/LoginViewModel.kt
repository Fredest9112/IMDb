package com.globant.imdb.model.loginfragment

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.imdb.repo.LoginRepo
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepo: LoginRepo) : ViewModel() {

    private var _googleLoginStatus = MutableLiveData<Boolean>()
    val googleLoginStatus: LiveData<Boolean> = _googleLoginStatus

    fun initLoggingProcess(defaultWebClientId: String, activity: Activity) {
        loginRepo.initFirebaseAuthWithGoogle(defaultWebClientId, activity)
    }

    fun initResultHandler(result: ActivityResult?) {
        viewModelScope.launch {
            _googleLoginStatus.value = loginRepo.signInResultHandler(result)
        }
    }

    fun signInGoogle(launcher: ActivityResultLauncher<Intent>) {
        loginRepo.signInGoogle(launcher)
    }
}