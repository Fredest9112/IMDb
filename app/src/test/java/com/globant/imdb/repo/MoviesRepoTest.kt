package com.globant.imdb.repo

import com.globant.imdb.api.IMDbApiService
import com.globant.imdb.data.Constants.API_KEY
import com.globant.imdb.data.MovieFromRemote
import com.globant.imdb.data.MoviesFromIMDbService
import com.globant.imdb.utils.NetworkResult
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.openMocks

@ExperimentalCoroutinesApi
class MoviesRepoTest {

    private lateinit var moviesRepo: MoviesRepo

    @Mock
    private lateinit var apiService: IMDbApiService
    private lateinit var autoCloseable: AutoCloseable

    @Before
    fun setup() {
        apiService = mock(IMDbApiService::class.java)
        moviesRepo = mock(MoviesRepo::class.java)
        autoCloseable = openMocks(this)
    }

    @After
    fun tearDown() {
        autoCloseable.close()
    }

    @Test
    fun testGetMoviesByQuerySuccess() = runTest {
        // Mock a successful response from the API
        val movieFromRemote: List<MovieFromRemote> = listOf(
            MovieFromRemote(
                id = 1,
                title = "Test Movie",
                overview = "This is a test movie",
                posterPath = "/test_poster.jpg",
                releaseDate = "2022-01-01",
                adult = false,
                backdropPath = "",
                genreIds = emptyList(),
                originalLanguage = "",
                originalTitle = "",
                popularity = 0.0,
                video = false,
                voteAverage = 0.0,
                vote_count = 0
            )
        )
        val topRatedMovies = MoviesFromIMDbService(
            page = 1, results = movieFromRemote, totalPages = 1, totalResults = 1
        )
        `when`(
            apiService.getMoviesFromQueryAsync(
                apiKey = API_KEY, query = "Test Movie"
            )
        ).thenReturn(
            CompletableDeferred(topRatedMovies)
        )

        assertEquals(1, topRatedMovies.results.size)
        assertEquals(movieFromRemote, topRatedMovies.results)
    }

    @Test
    fun testGetMoviesByQuery_Failure_NetworkError() = runTest {
        // given
        `when`(
            moviesRepo.getMoviesByQuery("test")
        ).thenReturn(NetworkResult.MoviesError("IMDb Network error: there's has been an error when trying to get data"))

        // when
        val result = moviesRepo.getMoviesByQuery("test")

        // then
        assertEquals(
            NetworkResult.MoviesError("IMDb Network error: there's has been an error when trying to get data"),
            result
        )
    }

    @Test
    fun testGetMoviesByQuery_Failure_HttpError() = runTest {
        // given
        `when`(
            moviesRepo.getMoviesByQuery("test")
        ).thenReturn(NetworkResult.MoviesError("IMDb HTTP error: there's has been an error when trying to get data"))

        // when
        val result = moviesRepo.getMoviesByQuery("test")

        // then
        assertEquals(
            NetworkResult.MoviesError("IMDb HTTP error: there's has been an error when trying to get data"),
            result
        )
    }

    @Test
    fun testGetMoviesByQuery_Failure_UnknownError() = runTest {
        // given
        `when`(
            moviesRepo.getMoviesByQuery("test")
        ).thenReturn(NetworkResult.MoviesError("Unknown error receiving data by search"))

        // when
        val result = moviesRepo.getMoviesByQuery("test")

        // then
        assertEquals(
            NetworkResult.MoviesError("Unknown error receiving data by search"),
            result
        )
    }

    @Test
    fun testGetMoviesByQuery_Failure_EmptyData() = runTest {
        // given
        val topRatedMovies = MoviesFromIMDbService(
            page = 1, results = emptyList(), totalPages = 1, totalResults = 1
        )
        `when`(
            apiService.getMoviesFromQueryAsync(
                apiKey = API_KEY, query = "Test Movie"
            )
        ).thenReturn(
            CompletableDeferred(topRatedMovies)
        )

        `when`(moviesRepo.getMoviesByQuery("test")).thenReturn(
            NetworkResult.MoviesError("MoviesRepo: there's no data...")
        )

        // when
        val result = moviesRepo.getMoviesByQuery("test")

        // then
        assertEquals(
            NetworkResult.MoviesError("MoviesRepo: there's no data..."),
            result
        )
        assert(topRatedMovies.results.isEmpty())
    }

    @Test
    fun testGetRecommendedMoviesById_Failure_NetworkError() = runTest {
        // given
        `when`(
            moviesRepo.getRecommendedMovies(1)
        ).thenReturn(NetworkResult.MoviesError("IMDb Network error: there's has been an error when trying to get data"))

        // when
        val result = moviesRepo.getRecommendedMovies(1)

        // then
        assertEquals(
            NetworkResult.MoviesError("IMDb Network error: there's has been an error when trying to get data"),
            result
        )
    }

    @Test
    fun testGetRecommendedMoviesById_Failure_HttpError() = runTest {
        // given
        `when`(
            moviesRepo.getRecommendedMovies(1)
        ).thenReturn(NetworkResult.MoviesError("IMDb HTTP error: there's has been an error when trying to get data"))

        // when
        val result = moviesRepo.getRecommendedMovies(1)

        // then
        assertEquals(
            NetworkResult.MoviesError("IMDb HTTP error: there's has been an error when trying to get data"),
            result
        )
    }

    @Test
    fun testGetRecommendedMoviesById_Failure_UnknownError() = runTest {
        // given
        `when`(
            moviesRepo.getRecommendedMovies(1)
        ).thenReturn(NetworkResult.MoviesError("Unknown error receiving data by search"))

        // when
        val result = moviesRepo.getRecommendedMovies(1)

        // then
        assertEquals(
            NetworkResult.MoviesError("Unknown error receiving data by search"),
            result
        )
    }
}
