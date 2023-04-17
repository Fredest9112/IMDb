package com.globant.imdb.api

import com.globant.imdb.data.Constants.INITIAL_SEARCH_SCREEN_PATH
import com.globant.imdb.data.Constants.MOST_POPULAR_MOVIES
import com.globant.imdb.data.Constants.SEARCH_MOVIE_PATH
import com.globant.imdb.data.MoviesFromIMDbService
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IMDbApiService {
    @GET(INITIAL_SEARCH_SCREEN_PATH)
    fun getTopRatedMoviesAsync(@Query("api_key") apiKey: String): Deferred<MoviesFromIMDbService>?

    @GET(SEARCH_MOVIE_PATH)
    fun getMoviesFromQueryAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en_US",
        @Query("query") query: String,
        @Query("page") page: String = "1",
        @Query("include_adult") includeAdult: Boolean = false
    ): Deferred<MoviesFromIMDbService>?

    @GET(MOST_POPULAR_MOVIES)
    fun getMostPopularMoviesAsync(@Query("api_key") apiKey: String): Deferred<MoviesFromIMDbService>?

    @GET("movie/{movie_id}/recommendations")
    fun getRecommendedMoviesAsync(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Deferred<MoviesFromIMDbService>?
}