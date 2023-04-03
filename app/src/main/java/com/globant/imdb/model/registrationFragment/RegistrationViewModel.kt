package com.globant.imdb.model.registrationFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.imdb.data.Constants
import com.globant.imdb.repo.LoginRepo
import com.globant.imdb.utils.AuthResult
import kotlinx.coroutines.launch

class RegistrationViewModel(private val loginRepo: LoginRepo) : ViewModel() {

    private var _loginStatus = MutableLiveData<AuthResult>()
    val loginStatus: LiveData<AuthResult> = _loginStatus

    private var _isUsernameValid = MutableLiveData<Boolean>()
    val isUsernameValid: LiveData<Boolean> = _isUsernameValid
    private lateinit var usernameInput: String

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
        _areInputsValid.addSource(_isUsernameValid) { updateInputs() }

        _isEmailValid.value = true
        _isPasswordValid.value = true
        _isUsernameValid.value = true
        _areInputsValid.value = false
    }

    fun setUsernameInput(username: String) {
        _isUsernameValid.value = isUsernameValid(username)
        _isEmailValid.value.let { if (it == true) usernameInput = username else Unit }
    }

    fun setEmailInput(email: String) {
        _isEmailValid.value = isEmailValid(email)
        _isEmailValid.value.let { if (it == true) emailInput = email else Unit }
    }

    fun setPasswordInput(password: String) {
        _isPasswordValid.value = isPasswordValid(password)
        _isEmailValid.value.let { if (it == true) passwordInput = password else Unit }
    }

    private fun isUsernameValid(username: String): Boolean {
        return username.isNotEmpty()
    }

    private fun isEmailValid(email: String): Boolean {
        return email.isNotEmpty() && email.contains("@")
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.matches(Constants.PASSWORD_PATTERN.toRegex())
    }

    private fun updateInputs() {
        _areInputsValid.value =
            isEmailValid.value == true && isPasswordValid.value == true && isUsernameValid.value == true
    }

    fun signUpWithEmailAndPassword() {
        viewModelScope.launch {
            _loginStatus.value = loginRepo.signUpEmailAndPass(emailInput, passwordInput)
        }
    }
}