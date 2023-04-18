package com.globant.imdb.model.profileFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.globant.imdb.database.RecentWatchedMovie
import com.globant.imdb.database.WatchListMovie
import com.globant.imdb.repo.DatabaseRepo

class ProfileViewModel(private val databaseRepo: DatabaseRepo) : ViewModel() {

    val recentWatchedMovies:LiveData<List<RecentWatchedMovie>> = getRecentWatchedMoviesFromDB()
    val watchListMovies:LiveData<List<WatchListMovie>> = getWatchListMoviesFromDB()

    private fun getWatchListMoviesFromDB(): LiveData<List<WatchListMovie>> {
        return liveData {
            databaseRepo.getWatchListMovies().collect{
                emit(it)
            }
        }
    }

    private fun getRecentWatchedMoviesFromDB(): LiveData<List<RecentWatchedMovie>> {
        return liveData {
            databaseRepo.getRecentWatchedMovies().collect{
                emit(it)
            }
        }
    }
}