package com.globant.imdb.database

import androidx.test.filters.SmallTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@SmallTest
class MovieExtensionFunctionsTest {

    private val watchListMovie1 = WatchListMovie(
        id = 1,
        posterPath = "posterPath1",
        title = "title1",
        originalTitle = "originalTitle1",
        overview = "overview1",
        releaseDate = "releaseDate1",
        voteAverage = 7.5,
        popularity = 123.45
    )

    private val watchListMovie2 = WatchListMovie(
        id = 2,
        posterPath = "posterPath2",
        title = "title2",
        originalTitle = "originalTitle2",
        overview = "overview2",
        releaseDate = "releaseDate2",
        voteAverage = 8.0,
        popularity = 234.56
    )

    private val recentWatchedMovie1 = RecentWatchedMovie(
        id = 1,
        posterPath = "posterPath1",
        title = "title1",
        originalTitle = "originalTitle1",
        overview = "overview1",
        releaseDate = "releaseDate1",
        voteAverage = 7.5,
        popularity = 123.45
    )

    private val recentWatchedMovie2 = RecentWatchedMovie(
        id = 2,
        posterPath = "posterPath2",
        title = "title2",
        originalTitle = "originalTitle2",
        overview = "overview2",
        releaseDate = "releaseDate2",
        voteAverage = 8.0,
        popularity = 234.56
    )

    private val movie1 = Movie(
        id = 1,
        posterPath = "posterPath1",
        title = "title1",
        originalTitle = "originalTitle1",
        overview = "overview1",
        releaseDate = "releaseDate1",
        voteAverage = 7.5,
        popularity = 123.45
    )

    private val movie2 = Movie(
        id = 2,
        posterPath = "posterPath2",
        title = "title2",
        originalTitle = "originalTitle2",
        overview = "overview2",
        releaseDate = "releaseDate2",
        voteAverage = 8.0,
        popularity = 234.56
    )

    @Test
    fun `asDBMovie() should return correct list of movies for WatchListMovie`() {
        val watchListMovies = listOf(watchListMovie1, watchListMovie2)
        val expectedMovies = listOf(movie1, movie2)
        val actualMovies = watchListMovies.asDBMovie()
        assertEquals(expectedMovies, actualMovies)
    }

    @Test
    fun `asDBMovie() should return correct list of movies for RecentWatchedMovie`() {
        val recentWatchedMovies = listOf(recentWatchedMovie1, recentWatchedMovie2)
        val expectedMovies = listOf(movie1, movie2)
        val actualMovies = recentWatchedMovies.asDBMovie()
        assertEquals(expectedMovies, actualMovies)
    }

    @Test
    fun `asWatchListMovie() should return correct WatchListMovie`() {
        val movie = movie1
        val expectedWatchListMovie = watchListMovie1
        assertEquals(expectedWatchListMovie, movie.asWatchListMovie())
    }

    @Test
    fun `asRecentWatchedMovie() should return correct RecentWatchedMovie`() {
        val movie = movie1
        val expectedRecentWatchedMovie = recentWatchedMovie1
        assertEquals(expectedRecentWatchedMovie, movie.asRecentWatchedMovie())
    }
}
