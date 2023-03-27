package com.globant.imdb.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseModule {
    @Singleton
    @Provides
    fun provideFirebase(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}