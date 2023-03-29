package com.globant.imdb.di

import android.content.Context
import com.globant.imdb.database.IMDbDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun providesIMDbDatabase(context: Context): IMDbDataBase {
        return IMDbDataBase.getInstance(context)
    }
}