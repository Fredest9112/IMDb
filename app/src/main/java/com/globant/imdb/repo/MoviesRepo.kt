package com.globant.imdb.repo

import android.util.Log
import com.globant.imdb.api.IMDbNetworking
import com.globant.imdb.data.Constants.API_KEY
import com.globant.imdb.data.Movie
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepo @Inject constructor() {
    suspend fun getTopRatedMovies(): List<Movie> {
        var topRatedMovies: List<Movie>? = null
        withContext(Dispatchers.IO) {
            try {
                val response = IMDbNetworking.movieData.getTopRatedMoviesAsync(
                    API_KEY
                )
                topRatedMovies = response.await().results
            } catch (e: Exception) {
                Log.e("Error receiving top rated movies", "${e.message}")
            }
        }
        return topRatedMovies ?: emptyList()
    }

//    fun getTopRatedMovies(): LiveData<List<Movie>> {
//        return liveData {
//            IMDbNetworking.movieData.getTopRatedMoviesAsync(API_KEY).collect{
//                emit(it.await().results)
//            }
//        }
//    }

    suspend fun getMoviesByQuery(query: String): List<Movie> {
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
        return movieListByQuery ?: emptyList()
    }
}