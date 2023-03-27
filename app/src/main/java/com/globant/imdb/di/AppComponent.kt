package com.globant.imdb.di

import com.globant.imdb.view.home.SearchFragment
import com.globant.imdb.view.login.LoginFragment
import com.globant.imdb.view.registration.RegistrationFragment
import com.globant.imdb.view.splash.SplashFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FirebaseModule::class])
interface AppComponent {
    fun injectLoginFragment(fragment: LoginFragment)
    fun injectRegistrationFragment(fragment: RegistrationFragment)
    fun injectSearchFragment(fragment: SearchFragment)
    fun injectSplashFragment(fragment: SplashFragment)
}