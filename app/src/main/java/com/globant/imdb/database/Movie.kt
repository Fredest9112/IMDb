package com.globant.imdb.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.globant.imdb.data.Constants.DATABASE_NAME

@Entity(tableName = DATABASE_NAME)
data class Movie(
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