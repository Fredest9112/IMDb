package com.globant.imdb.model.searchFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.imdb.data.Movie
import com.globant.imdb.repo.MoviesRepo
import kotlinx.coroutines.launch

class SearchMovieViewModel(private val moviesRepo: MoviesRepo) : ViewModel() {

    private var _topRatedMovies = MutableLiveData<List<Movie>>()
    val topRatedMovies: LiveData<List<Movie>> = _topRatedMovies

    private var _moviesFromQuery = MutableLiveData<List<Movie>>()
    val moviesFromQuery: LiveData<List<Movie>> = _moviesFromQuery

    init {
        viewModelScope.launch {
            _topRatedMovies.value = moviesRepo.getTopRatedMovies()
        }
    }

    fun getMoviesFromQuery(query: String) {
        viewModelScope.launch {
            _moviesFromQuery.value = moviesRepo.getMoviesByQuery(query)
        }
    }
}