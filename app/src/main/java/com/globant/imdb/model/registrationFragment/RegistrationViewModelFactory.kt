package com.globant.imdb.model.registrationFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.globant.imdb.repo.LoginRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Suppress("UNCHECKED_CAST")
class RegistrationViewModelFactory @Inject constructor(private val loginRepo: LoginRepo) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegistrationViewModel(loginRepo) as T
    }
}