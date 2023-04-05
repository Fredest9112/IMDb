package com.globant.imdb.model.searchFragment

import androidx.lifecycle.*
import com.globant.imdb.database.Movie
import com.globant.imdb.repo.DatabaseRepo
import com.globant.imdb.repo.MoviesRepo
import com.globant.imdb.utils.NetworkResult
import kotlinx.coroutines.launch

class SearchMovieViewModel(private val moviesRepo: MoviesRepo, private val databaseRepo: DatabaseRepo) : ViewModel() {

    val topRatedMovies: LiveData<List<Movie>> = getTopRatedMoviesFromDB()

    private var _moviesFromQuery = MutableLiveData<NetworkResult>()
    val moviesFromQuery: LiveData<NetworkResult> = _moviesFromQuery

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
}