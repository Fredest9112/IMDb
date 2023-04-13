package com.globant.imdb.model.homeFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.imdb.repo.MoviesRepo
import com.globant.imdb.utils.NetworkResult
import kotlinx.coroutines.launch

class HomeViewModel(private val moviesRepo: MoviesRepo) : ViewModel() {

    private var _mostPopularMovies = MutableLiveData<NetworkResult>()
    val mostPopularMovies: LiveData<NetworkResult> = _mostPopularMovies

    init {
        getMostPopularMovies()
    }

    private fun getMostPopularMovies(){
        viewModelScope.launch {
            _mostPopularMovies.value = moviesRepo.getMostPopularMovies()
        }
    }
}