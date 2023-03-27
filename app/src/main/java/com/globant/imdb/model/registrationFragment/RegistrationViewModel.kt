package com.globant.imdb.model.registrationFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.imdb.data.Constants
import com.globant.imdb.repo.LoginRepo
import kotlinx.coroutines.launch

class RegistrationViewModel(private val loginRepo: LoginRepo):ViewModel() {

    private var _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> = _loginStatus

    private var _usernameInput = MutableLiveData<String>()

    private var _emailInput = MutableLiveData<String>()

    private var _passwordInput = MutableLiveData<String>()

    private var _areInputsValid = MutableLiveData<Boolean>()
    val areInputsValid: LiveData<Boolean> = _areInputsValid

    init {
        loginRepo.initFirebaseInstance()
    }

    fun setUsernameInput(username: String) {
        _usernameInput.value = username
        _areInputsValid.value = isUsernameValid() && isEmailValid() && isPasswordValid()
    }

    fun setEmailInput(email: String){
        _emailInput.value = email
        _areInputsValid.value = isUsernameValid() && isEmailValid() && isPasswordValid()
    }

    fun setPasswordInput(password: String) {
        _passwordInput.value = password
        _areInputsValid.value = isUsernameValid() && isEmailValid() && isPasswordValid()
    }

    private fun isUsernameValid(): Boolean {
        return !_usernameInput.value.isNullOrEmpty()
    }

    private fun isEmailValid(): Boolean {
        return _emailInput.value?.contains("@") ?: false
    }

    private fun isPasswordValid(): Boolean {
        val pattern = Constants.PASSWORD_PATTERN.toRegex()
        return _passwordInput.value?.matches(pattern) ?: false
    }

    fun signUpWithEmailAndPassword() {
        viewModelScope.launch {
            _loginStatus.value = loginRepo.signUpEmailAndPass(
                _emailInput.value.toString(),
                _passwordInput.value.toString()
            )
        }
    }
}