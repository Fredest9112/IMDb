package com.globant.imdb.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.globant.imdb.data.Constants.MOVIES_TABLE

@Entity(tableName = MOVIES_TABLE)
data class Movie(
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

fun Movie?.asFavoriteMovie(): RecentWatchedMovie {
    return RecentWatchedMovie(
        id = this?.id ?: 0,
        posterPath = this?.posterPath ?: "",
        title = this?.title ?: "",
        originalTitle = this?.originalTitle ?: "",
        overview = this?.overview ?: "",
        releaseDate = this?.releaseDate ?: "",
        voteAverage = this?.voteAverage ?: 0.0,
        popularity = this?.popularity ?: 0.0
    )
}

fun Movie?.asWatchListMovie(): WatchListMovie {
    return WatchListMovie(
        id = this?.id ?: 0,
        posterPath = this?.posterPath ?: "",
        title = this?.title ?: "",
        originalTitle = this?.originalTitle ?: "",
        overview = this?.overview ?: "",
        releaseDate = this?.releaseDate ?: "",
        voteAverage = this?.voteAverage ?: 0.0,
        popularity = this?.popularity ?: 0.0
    )
}