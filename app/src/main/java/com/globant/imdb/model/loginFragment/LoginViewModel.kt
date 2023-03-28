package com.globant.imdb.model.loginFragment

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.imdb.data.Constants.PASSWORD_PATTERN
import com.globant.imdb.repo.LoginRepo
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepo: LoginRepo) : ViewModel() {

    private var _googleLoginStatus = MutableLiveData<Boolean>()
    val googleLoginStatus: LiveData<Boolean> = _googleLoginStatus

    private var _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> = _loginStatus

    private var _emailInput = MutableLiveData<String>()

    private var _passwordInput = MutableLiveData<String>()

    private var _areInputsValid = MutableLiveData<Boolean>()
    val areInputsValid: LiveData<Boolean> = _areInputsValid

    fun setEmailInput(username: String) {
        _emailInput.value = username
        _areInputsValid.value = isEmailValid() && isPasswordValid()
    }

    fun setPasswordInput(password: String) {
        _passwordInput.value = password
        _areInputsValid.value = isEmailValid() && isPasswordValid()
    }

    private fun isEmailValid(): Boolean {
        return !_emailInput.value.isNullOrEmpty()
    }

    private fun isPasswordValid(): Boolean {
        val pattern = PASSWORD_PATTERN.toRegex()
        return _passwordInput.value?.matches(pattern) ?: false
    }

    fun initLoggingProcess(defaultWebClientId: String, activity: Activity) {
        loginRepo.initAuthenticationWithGoogle(defaultWebClientId, activity)
    }

    fun initResultHandler(result: ActivityResult?) {
        viewModelScope.launch {
            _googleLoginStatus.value = loginRepo.signInResultHandler(result)
        }
    }

    fun signInGoogle(launcher: ActivityResultLauncher<Intent>) {
        loginRepo.signInGoogle(launcher)
    }

    fun signInWithEmailAndPassword() {
        viewModelScope.launch {
            _loginStatus.value = loginRepo.signInEmailAndPass(
                _emailInput.value.toString(),
                _passwordInput.value.toString()
            )
        }
    }
}