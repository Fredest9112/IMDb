package com.globant.imdb.repo

import android.util.Log
import com.globant.imdb.api.IMDbNetworking
import com.globant.imdb.data.Constants
import com.globant.imdb.data.asDBModel
import com.globant.imdb.database.IMDbDataBase
import com.globant.imdb.database.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DatabaseRepo @Inject constructor(private val imDbDataBase: IMDbDataBase) {

    suspend fun insertMoviesOnDB(): Boolean {
        return runCatching {
            val response = IMDbNetworking.movieData.getTopRatedMoviesAsync(
                Constants.API_KEY
            ).await()

            response.results.asDBModel().forEach {
                imDbDataBase.imDbDao.insertMovie(it)
            }
            true
        }.getOrElse {
            Log.e("Error receiving top rated movies", "${it.message}")
            false
        }
    }

    fun getTopRatedMovies(): Flow<List<Movie>> {
        return imDbDataBase.imDbDao.getTopRatedMovies()
    }

    suspend fun deleteAllMovies(){
        withContext(Dispatchers.IO) {
            imDbDataBase.imDbDao.deleteAll()
        }
    }
}