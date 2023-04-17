package com.globant.imdb.repo

import com.globant.imdb.api.IMDbNetworking
import com.globant.imdb.data.Constants.API_KEY
import com.globant.imdb.data.asDBModel
import com.globant.imdb.utils.NetworkResult
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class MoviesRepo @Inject constructor() {

    suspend fun getMoviesByQuery(query: String): NetworkResult {
        return withContext(Dispatchers.IO) {
            try {
                val response = IMDbNetworking.movieData.getMoviesFromQueryAsync(
                    apiKey = API_KEY, query = query
                )
                val movieListByQuery = response?.await()?.results
                movieListByQuery?.asDBModel()?.let { NetworkResult.MoviesSuccess("", it) }
                    ?: NetworkResult.MoviesError("MoviesRepo: there's no data...")
            } catch (e: HttpException) {
                NetworkResult.MoviesError("IMDb HTTP error: there's has been an error when trying to get data ${e.code()} - ${e.message()}")
            } catch (e: IOException) {
                NetworkResult.MoviesError("IMDb Network error: there's has been an error when trying to get data ${e.message}")
            } catch (e: Exception) {
                NetworkResult.MoviesError("Unknown error receiving data by search: ${e.message}")
            }
        }
    }

    suspend fun getTopRatedMovies(): NetworkResult {
        return withContext(Dispatchers.IO) {
            try {
                val response = IMDbNetworking.movieData.getTopRatedMoviesAsync(API_KEY)
                val topRatedMovieList = response?.await()?.results
                topRatedMovieList?.asDBModel()?.let { NetworkResult.MoviesSuccess("", it) }
                    ?: NetworkResult.MoviesError("MoviesRepo: there's no data...")
            } catch (e: HttpException) {
                NetworkResult.MoviesError("IMDb HTTP error: there's has been an error when trying to get data ${e.code()} - ${e.message()}")
            } catch (e: IOException) {
                NetworkResult.MoviesError("IMDb Network error: there's has been an error when trying to get data ${e.message}")
            } catch (e: Exception) {
                NetworkResult.MoviesError("Unknown error receiving data by search: ${e.message}")
            }
        }
    }

    suspend fun getMostPopularMovies(): NetworkResult {
        return withContext(Dispatchers.IO) {
            try {
                val response = IMDbNetworking.movieData.getMostPopularMoviesAsync(API_KEY)
                val topRatedMovieList = response?.await()?.results
                topRatedMovieList?.asDBModel()?.let { NetworkResult.MoviesSuccess("", it) }
                    ?: NetworkResult.MoviesError("MoviesRepo: there's no data...")
            } catch (e: HttpException) {
                NetworkResult.MoviesError("IMDb HTTP error: there's has been an error when trying to get data ${e.code()} - ${e.message()}")
            } catch (e: IOException) {
                NetworkResult.MoviesError("IMDb Network error: there's has been an error when trying to get data ${e.message}")
            } catch (e: Exception) {
                NetworkResult.MoviesError("Unknown error receiving data by search: ${e.message}")
            }
        }
    }

    suspend fun getRecommendedMovies(id: Int): NetworkResult {
        return withContext(Dispatchers.IO) {
            try {
                val response = IMDbNetworking.movieData.getRecommendedMoviesAsync(id, API_KEY)
                val topRatedMovieList = response?.await()?.results
                topRatedMovieList?.asDBModel()?.let { NetworkResult.MoviesSuccess("", it) }
                    ?: NetworkResult.MoviesError("MoviesRepo: there's no data...")
            } catch (e: HttpException) {
                NetworkResult.MoviesError("IMDb HTTP error: there's has been an error when trying to get data ${e.code()} - ${e.message()}")
            } catch (e: IOException) {
                NetworkResult.MoviesError("IMDb Network error: there's has been an error when trying to get data ${e.message}")
            } catch (e: Exception) {
                NetworkResult.MoviesError("Unknown error receiving data by search: ${e.message}")
            }
        }
    }
}