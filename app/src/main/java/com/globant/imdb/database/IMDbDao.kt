package com.globant.imdb.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.globant.imdb.data.Constants.FAV_MOVIES_TABLE
import com.globant.imdb.data.Constants.MOVIES_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface IMDbDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Query("SELECT * FROM $MOVIES_TABLE")
    fun getTopRatedMovies(): Flow<List<Movie>>

    @Query("DELETE FROM $MOVIES_TABLE")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(favoriteMovie: FavoriteMovie)

    @Query("SELECT * FROM $FAV_MOVIES_TABLE")
    fun getFavoriteMovies(): Flow<List<FavoriteMovie>>

    @Query("DELETE FROM $FAV_MOVIES_TABLE")
    fun deleteFavoriteMovies()
}