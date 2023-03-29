package com.globant.imdb.model.searchFragment

import androidx.lifecycle.*
import com.globant.imdb.database.MovieInDB
import com.globant.imdb.repo.DatabaseRepo
import com.globant.imdb.repo.MoviesRepo
import kotlinx.coroutines.launch

class SearchMovieViewModel(private val moviesRepo: MoviesRepo, private val databaseRepo: DatabaseRepo) : ViewModel() {

    val topRatedMovies: LiveData<List<MovieInDB>> = getTopRatedMoviesFromDB()

    private var _moviesFromQuery = MutableLiveData<List<MovieInDB>>()
    val moviesFromQuery: LiveData<List<MovieInDB>> = _moviesFromQuery

    private fun getTopRatedMoviesFromDB(): LiveData<List<MovieInDB>> {
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
}