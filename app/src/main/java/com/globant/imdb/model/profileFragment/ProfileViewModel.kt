package com.globant.imdb.model.profileFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.globant.imdb.database.FavoriteMovie
import com.globant.imdb.repo.DatabaseRepo

class ProfileViewModel(private val databaseRepo: DatabaseRepo) : ViewModel() {

    val favoriteMovies:LiveData<List<FavoriteMovie>> = getFavoriteMoviesFromDB()

    private fun getFavoriteMoviesFromDB(): LiveData<List<FavoriteMovie>> {
        return liveData {
            databaseRepo.getFavoriteMovies().collect{
                emit(it)
            }
        }
    }
}