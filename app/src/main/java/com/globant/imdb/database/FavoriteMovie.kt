package com.globant.imdb.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.globant.imdb.data.Constants.FAV_MOVIES_TABLE

@Entity(tableName = FAV_MOVIES_TABLE)
data class FavoriteMovie(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val posterPath: String,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Double,
    val popularity: Double,
)