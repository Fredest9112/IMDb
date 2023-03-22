package com.globant.imdb.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.imdb.data.Movie
import com.globant.imdb.repo.MoviesRepo
import kotlinx.coroutines.launch

class SearchMovieViewModel(moviesRepo: MoviesRepo): ViewModel() {

    private var _topRatedMovies = MutableLiveData<List<Movie>>()
    val topRatedMovies : LiveData<List<Movie>> = _topRatedMovies

    init {
        viewModelScope.launch {
            _topRatedMovies.value = moviesRepo.getTopRatedMovies()
        }
    }
}