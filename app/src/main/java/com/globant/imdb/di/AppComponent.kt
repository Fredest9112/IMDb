package com.globant.imdb.di

import com.globant.imdb.view.home.HomeFragment
import com.globant.imdb.view.home.search.DetailsFragment
import com.globant.imdb.view.home.search.SearchFragment
import com.globant.imdb.view.login.LoginFragment
import com.globant.imdb.view.registration.RegistrationFragment
import com.globant.imdb.view.splash.SplashFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FirebaseModule::class, AppModule::class, DatabaseModule::class])
interface AppComponent {
    fun injectLoginFragment(fragment: LoginFragment)
    fun injectRegistrationFragment(fragment: RegistrationFragment)
    fun injectSearchFragment(fragment: SearchFragment)
    fun injectSplashFragment(fragment: SplashFragment)
    fun injectDetailsFragment(fragment: DetailsFragment)
    fun injectHomeFragment(fragment: HomeFragment)
}