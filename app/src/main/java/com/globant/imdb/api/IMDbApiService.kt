package com.globant.imdb.api

import com.globant.imdb.data.Constants.INITIAL_SEARCH_SCREEN_PATH
import com.globant.imdb.data.Constants.SEARCH_MOVIE_PATH_1
import com.globant.imdb.data.TopRatedMovies
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface IMDbApiService {
    @GET(INITIAL_SEARCH_SCREEN_PATH)
    fun getTopRatedMoviesAsync(@Query("api_key") apiKey: String): Deferred<TopRatedMovies>

    @GET(SEARCH_MOVIE_PATH_1)
    fun getMoviesFromQueryAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en_US",
        @Query("query") query: String,
        @Query("page") page:String = "1",
        @Query("include_adult") includeAdult: Boolean = false
    ):Deferred<TopRatedMovies>
}