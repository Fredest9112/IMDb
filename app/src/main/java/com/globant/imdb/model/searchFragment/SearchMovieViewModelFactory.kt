package com.globant.imdb.model.searchFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.globant.imdb.repo.MoviesRepo
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class SearchMovieViewModelFactory @Inject constructor(private val moviesRepo: MoviesRepo) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchMovieViewModel(moviesRepo) as T
    }
}