package com.globant.imdb.api

import com.globant.imdb.data.Constants.INITIAL_SEARCH_SCREEN_PATH
import com.globant.imdb.data.TopRatedMovies
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface IMDbApiService {
    @GET(INITIAL_SEARCH_SCREEN_PATH)
    fun getTopRatedMoviesAsync(@Query("api_key") apiKey: String): Deferred<TopRatedMovies>
}