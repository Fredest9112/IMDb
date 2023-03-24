package com.globant.imdb.model.loginfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.globant.imdb.repo.LoginRepo

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(private val loginRepo: LoginRepo):
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(loginRepo) as T
    }
}