package com.globant.imdb.repo

import android.util.Log
import com.globant.imdb.api.IMDbNetworking
import com.globant.imdb.data.Constants
import com.globant.imdb.data.Movie
import com.globant.imdb.data.asDBModel
import com.globant.imdb.database.IMDbDataBase
import com.globant.imdb.database.MovieInDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class DatabaseRepo(private val imDbDataBase: IMDbDataBase) {

    suspend fun insertMoviesOnDB(): Boolean {
        var topRatedMovies: List<Movie>?
        withContext(Dispatchers.IO) {
            try {
                val response = IMDbNetworking.movieData.getTopRatedMoviesAsync(
                    Constants.API_KEY
                )
                topRatedMovies = response.await().results
                topRatedMovies?.asDBModel()?.forEach {
                    imDbDataBase.imDbDao.insertMovie(it)
                }
                return@withContext true
            } catch (e: Exception) {
                Log.e("Error receiving top rated movies", "${e.message}")
                return@withContext false
            }
        }
        return false
    }

    fun getTopRatedMovies(): Flow<List<MovieInDB>> {
        return imDbDataBase.imDbDao.getTopRatedMovies()
    }

    fun deleteAllMovies(){
        imDbDataBase.imDbDao.deleteAll()
    }


}