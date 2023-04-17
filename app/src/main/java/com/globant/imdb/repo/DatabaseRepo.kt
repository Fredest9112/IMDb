package com.globant.imdb.repo

import com.globant.imdb.database.FavoriteMovie
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
            DatabaseResult.DatabaseError("Error inserting movies on database: $e")
        }
    }

    fun getTopRatedMovies(): Flow<List<Movie>> {
        return imDbDataBase.imDbDao.getTopRatedMovies()
    }

    suspend fun deleteAllMovies(): DatabaseResult {
        return try{
            withContext(Dispatchers.IO) {
                imDbDataBase.imDbDao.deleteAll()
            }
            DatabaseResult.DatabaseSuccess("")
        } catch (e: Exception) {
            DatabaseResult.DatabaseError("Error deleting movies on database: $e")
        }
    }

    suspend fun insertFavoriteMoviesOnDB(favoriteMovie: FavoriteMovie): DatabaseResult {
        return try {
            imDbDataBase.imDbDao.insertFavoriteMovie(favoriteMovie)
            DatabaseResult.DatabaseSuccess("")
        } catch (e: Exception) {
            DatabaseResult.DatabaseError("Error inserting movies on database: $e")
        }
    }

    fun getFavoriteMovies(): Flow<List<FavoriteMovie>> {
        return imDbDataBase.imDbDao.getFavoriteMovies()
    }

    suspend fun deleteAllFavoriteMovies(): DatabaseResult {
        return try {
            withContext(Dispatchers.IO) {
                imDbDataBase.imDbDao.deleteFavoriteMovies()
                DatabaseResult.DatabaseSuccess("")
            }
        } catch (e: Exception) {
            DatabaseResult.DatabaseError("Error deleting favorite movies on database: $e")
        }
    }
}