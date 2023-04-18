package com.globant.imdb.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.globant.imdb.data.Constants.WATCHLIST_MOVIES_TABLE

@Entity(tableName = WATCHLIST_MOVIES_TABLE)
data class WatchListMovie(
    @PrimaryKey
    val id: Int,
    val posterPath: String,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Double,
    val popularity: Double,
)

fun List<WatchListMovie?>.asDBMovie(): List<Movie> {
    return mapNotNull {
        Movie(
            id = it?.id ?: 0,
            posterPath = it?.posterPath ?: "",
            title = it?.title ?: "",
            originalTitle = it?.originalTitle ?: "",
            overview = it?.overview ?: "",
            releaseDate = it?.releaseDate ?: "",
            voteAverage = it?.voteAverage ?: 0.0,
            popularity = it?.popularity ?: 0.0,
        )
    }
}