package com.globant.imdb.utils

import com.globant.imdb.database.Movie

sealed class NetworkResult {
    data class MoviesSuccess (val message: String, val movies: List<Movie>): NetworkResult()
    data class MoviesError (val message: String, val movies: List<Movie> = emptyList()): NetworkResult()
}
