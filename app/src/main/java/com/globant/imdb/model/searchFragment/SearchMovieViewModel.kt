package com.globant.imdb.model.searchFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.globant.imdb.database.Movie
import com.globant.imdb.repo.DatabaseRepo
import com.globant.imdb.repo.MoviesRepo
import com.globant.imdb.utils.NetworkResult
import kotlinx.coroutines.launch

class SearchMovieViewModel(private val moviesRepo: MoviesRepo, private val databaseRepo: DatabaseRepo) : ViewModel() {

    val topRatedMovies: LiveData<List<Movie>> = getTopRatedMoviesFromDB()

    private var _moviesFromQuery = MutableLiveData<NetworkResult>()
    val moviesFromQuery: LiveData<NetworkResult> = _moviesFromQuery

    private var _clickedMovie = MutableLiveData<Movie>()
    val clickedMovie: LiveData<Movie> = _clickedMovie

    private fun getTopRatedMoviesFromDB(): LiveData<List<Movie>> {
        return liveData {
            databaseRepo.getTopRatedMovies().collect{
                emit(it)
            }
        }
    }

    fun getMoviesFromQuery(query: String) {
        viewModelScope.launch {
            _moviesFromQuery.value = moviesRepo.getMoviesByQuery(query)
        }
    }

    fun getClickedMovie(movie: Movie){
        _clickedMovie.value = movie
    }
}