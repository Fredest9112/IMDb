package com.globant.imdb.repo

import android.util.Log
import com.globant.imdb.api.IMDbNetworking
import com.globant.imdb.data.Constants.API_KEY
import com.globant.imdb.data.Movie
import com.globant.imdb.data.asDBModel
import com.globant.imdb.database.MovieInDB
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepo @Inject constructor() {

    suspend fun getMoviesByQuery(query: String): List<MovieInDB> {
        var movieListByQuery: List<Movie>? = null
        withContext(Dispatchers.IO) {
            try {
                val response = IMDbNetworking.movieData.getMoviesFromQueryAsync(
                    apiKey = API_KEY, query = query
                )
                movieListByQuery = response.await().results
            } catch (e: Exception) {
                Log.e("Error receiving movies by search", "${e.message}")
            }
        }
        return movieListByQuery?.asDBModel() ?: emptyList()
    }
}