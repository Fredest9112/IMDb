package com.globant.imdb.model.homeFragment

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

class HomeViewModel(private val moviesRepo: MoviesRepo, private val databaseRepo: DatabaseRepo) :
    ViewModel() {

    val topRatedMovies: LiveData<List<Movie>> = getTopRatedMoviesFromDB()

    private var _allPopularMovies = MutableLiveData<NetworkResult>()
    val allPopularMovies: LiveData<NetworkResult> = _allPopularMovies

    private var _theMostPopularMovie = MutableLiveData<Movie>()
    val theMostPopularMovie: LiveData<Movie> = _theMostPopularMovie

    init {
        setMostPopularMovies()
    }

    private fun setMostPopularMovies() {
        viewModelScope.launch {
            _allPopularMovies.value = moviesRepo.getMostPopularMovies()
        }
    }

    private fun getTopRatedMoviesFromDB(): LiveData<List<Movie>> {
        return liveData {
            databaseRepo.getTopRatedMovies().collect {
                emit(it)
            }
        }
    }

    fun setMostPopularMovie(movies: List<Movie>) {
        _theMostPopularMovie.value = movies.maxByOrNull { it.popularity }
    }

    fun getRestOfPopularMovies(movies: List<Movie>) : List<Movie> {
        val restOfPopularMovies = movies.toMutableList()
        restOfPopularMovies.remove(_theMostPopularMovie.value)
        return restOfPopularMovies
    }
}