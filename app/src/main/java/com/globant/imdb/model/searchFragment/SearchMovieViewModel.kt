package com.globant.imdb.model.searchFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.globant.imdb.database.RecentWatchedMovie
import com.globant.imdb.database.Movie
import com.globant.imdb.database.WatchListMovie
import com.globant.imdb.database.asFavoriteMovie
import com.globant.imdb.repo.DatabaseRepo
import com.globant.imdb.repo.MoviesRepo
import com.globant.imdb.utils.NetworkResult
import kotlinx.coroutines.launch

class SearchMovieViewModel(private val moviesRepo: MoviesRepo, private val databaseRepo: DatabaseRepo) : ViewModel() {

    val topRatedMovies: LiveData<List<Movie>> = getTopRatedMoviesFromDB()

    private var _moviesFromQuery = MutableLiveData<NetworkResult>()
    val moviesFromQuery: LiveData<NetworkResult> = _moviesFromQuery

    private var _recommendedMovies = MutableLiveData<NetworkResult>()
    val recommendedMovies: LiveData<NetworkResult> = _recommendedMovies

    private var _clickedMovie = MutableLiveData<Movie>()
    val clickedMovie: LiveData<Movie> = _clickedMovie

    private var _clickedMovieId = MutableLiveData<Int>()
    val clickedMovieId: LiveData<Int> = _clickedMovieId

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
        _clickedMovieId.value = movie.id
        addFavoriteMovieToDB(movie.asFavoriteMovie())
    }

    fun getRecommendedMoviesFromId(id: Int) {
        viewModelScope.launch {
            _recommendedMovies.value = moviesRepo.getRecommendedMovies(id)
        }
    }

    private fun addFavoriteMovieToDB(favoriteMovie: RecentWatchedMovie){
        viewModelScope.launch {
            databaseRepo.insertRecentWatchedMoviesOnDB(favoriteMovie)
        }
    }

    fun addMovieToWatchList(watchListMovie: WatchListMovie){
        viewModelScope.launch {
            databaseRepo.insertWatchListMoviesOnDB(watchListMovie)
        }
    }
}