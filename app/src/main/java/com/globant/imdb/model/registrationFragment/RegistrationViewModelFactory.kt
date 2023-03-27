package com.globant.imdb.model.registrationFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.globant.imdb.repo.LoginRepo

@Suppress("UNCHECKED_CAST")
class RegistrationViewModelFactory(private val loginRepo: LoginRepo): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegistrationViewModel(loginRepo) as T
    }
}