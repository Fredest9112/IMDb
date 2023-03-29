package com.globant.imdb.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.globant.imdb.data.Constants.DATABASE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface IMDbDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Query("SELECT * FROM $DATABASE_NAME")
    fun getTopRatedMovies(): Flow<List<Movie>>

    @Query("DELETE FROM $DATABASE_NAME")
    fun deleteAll()
}