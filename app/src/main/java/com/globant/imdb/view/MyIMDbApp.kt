package com.globant.imdb.view

import android.app.Application
import com.globant.imdb.di.AppComponent
import com.globant.imdb.di.DaggerAppComponent

open class MyIMDbApp : Application() {

    open val appComponent: AppComponent by lazy {
        DaggerAppComponent.create()
    }
}