package com.globant.imdb.data

import com.google.gson.annotations.SerializedName

data class TopRatedMovies(
    val page: Int,
    val results: List<MovieFromRemote>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)