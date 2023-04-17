package com.globant.imdb.di

import com.globant.imdb.view.adapter.MostPopularMovieAdapter
import com.globant.imdb.view.adapter.SearchMovieAdapter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AdapterModule {

    @Singleton
    @Provides
    fun providesMostPopularMovieAdapter(): MostPopularMovieAdapter {
        return MostPopularMovieAdapter()
    }

    @Singleton
    @Provides
    fun providesSearchMovieAdapter(): SearchMovieAdapter {
        return SearchMovieAdapter()
    }
}