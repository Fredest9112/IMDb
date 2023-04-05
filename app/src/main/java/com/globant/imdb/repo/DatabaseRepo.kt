package com.globant.imdb.repo

import com.globant.imdb.database.IMDbDataBase
import com.globant.imdb.database.Movie
import com.globant.imdb.utils.DatabaseResult
import com.globant.imdb.utils.NetworkResult
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class DatabaseRepo @Inject constructor(
    private val imDbDataBase: IMDbDataBase,
    private val moviesRepo: MoviesRepo
) {

    suspend fun insertMoviesOnDB(): DatabaseResult {
        return try {
            when (val moviesResult = moviesRepo.getTopRatedMovies()) {
                is NetworkResult.MoviesSuccess -> {
                    moviesResult.movies.forEach {
                        imDbDataBase.imDbDao.insertMovie(it)
                    }
                    DatabaseResult.DatabaseSuccess("")
                }
                is NetworkResult.MoviesError -> {
                    moviesResult.movies.forEach {
                        imDbDataBase.imDbDao.insertMovie(it)
                    }
                    DatabaseResult.DatabaseError(moviesResult.message)
                }
            }
        } catch (e: Exception) {
            DatabaseResult.DatabaseError("Error inserting data on database: $e")
        }
    }

    fun getTopRatedMovies(): Flow<List<Movie>> {
        return imDbDataBase.imDbDao.getTopRatedMovies()
    }

    suspend fun deleteAllMovies() {
        withContext(Dispatchers.IO) {
            imDbDataBase.imDbDao.deleteAll()
        }
    }
}