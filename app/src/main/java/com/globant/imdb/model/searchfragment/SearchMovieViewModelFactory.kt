package com.globant.imdb.model.searchfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.globant.imdb.repo.MoviesRepo

@Suppress("UNCHECKED_CAST")
class SearchMovieViewModelFactory(private val moviesRepo: MoviesRepo):
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchMovieViewModel(moviesRepo) as T
    }
}