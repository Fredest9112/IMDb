package com.globant.imdb.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.globant.imdb.data.Constants.RECENT_WATCHED_MOVIES_TABLE
import com.globant.imdb.data.Constants.MOVIES_TABLE
import com.globant.imdb.data.Constants.WATCHLIST_MOVIES_TABLE
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
    suspend fun insertRecentWatchedMovie(recentWatchedMovie: RecentWatchedMovie)

    @Query("SELECT * FROM $RECENT_WATCHED_MOVIES_TABLE")
    fun getRecentWatchedMovies(): Flow<List<RecentWatchedMovie>>

    @Query("DELETE FROM $RECENT_WATCHED_MOVIES_TABLE")
    fun deleteRecentWatchedMovies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWatchListMovie(watchListMovie: WatchListMovie)

    @Query("SELECT * FROM $WATCHLIST_MOVIES_TABLE")
    fun getWatchListMovies(): Flow<List<WatchListMovie>>

    @Query("DELETE FROM $WATCHLIST_MOVIES_TABLE")
    fun deleteWatchListMovies()
}