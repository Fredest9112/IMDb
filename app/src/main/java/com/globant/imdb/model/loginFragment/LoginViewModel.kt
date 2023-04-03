package com.globant.imdb.model.loginFragment

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.imdb.data.Constants.PASSWORD_PATTERN
import com.globant.imdb.repo.LoginRepo
import com.globant.imdb.utils.AuthResult
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepo: LoginRepo) : ViewModel() {

    private var _googleLoginStatus = MutableLiveData<AuthResult>()
    val googleLoginStatus: LiveData<AuthResult> = _googleLoginStatus

    private var _loginStatus = MutableLiveData<AuthResult>()
    val loginStatus: LiveData<AuthResult> = _loginStatus

    private var _isEmailValid = MutableLiveData<Boolean>()
    val isEmailValid: LiveData<Boolean> = _isEmailValid
    private lateinit var emailInput: String

    private var _isPasswordValid = MutableLiveData<Boolean>()
    val isPasswordValid: LiveData<Boolean> = _isPasswordValid
    private lateinit var passwordInput: String

    private var _areInputsValid = MediatorLiveData<Boolean>()
    val areInputsValid: LiveData<Boolean> = _areInputsValid

    init {
        _areInputsValid.addSource(_isEmailValid) { updateInputs() }
        _areInputsValid.addSource(_isPasswordValid) { updateInputs() }

        _isEmailValid.value = true
        _isPasswordValid.value = true
        _areInputsValid.value = false
    }

    fun setEmailInput(email: String) {
        _isEmailValid.value = isEmailValid(email)
        _isEmailValid.value.let { if (it == true) emailInput = email else Unit }
    }

    fun setPasswordInput(password: String) {
        _isPasswordValid.value = isPasswordValid(password)
        _isEmailValid.value.let { if (it == true) passwordInput = password else Unit }
    }

    private fun updateInputs() {
        _areInputsValid.value = isEmailValid.value == true && isPasswordValid.value == true
    }

    private fun isEmailValid(email: String): Boolean {
        return email.isNotEmpty() && email.contains("@")
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.matches(PASSWORD_PATTERN.toRegex())
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
            _loginStatus.value = loginRepo.signInEmailAndPass(emailInput, passwordInput)
        }
    }
}