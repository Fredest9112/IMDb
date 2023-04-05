package com.globant.imdb.database

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.globant.imdb.TestDispatcherRule
import com.globant.imdb.data.MoviesRepoMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.util.concurrent.Executors

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
@Config(sdk = [Build.VERSION_CODES.Q])
class IMDbDaoTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    @get: Rule
    val dispatcherRule = TestDispatcherRule()


    private lateinit var imDbDataBase: IMDbDataBase

    @Before
    fun initIMDbDatabase(){
        imDbDataBase = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            IMDbDataBase::class.java
        ).allowMainThreadQueries()
            .setTransactionExecutor(Executors.newSingleThreadExecutor())
            .build()
    }

    @After
    fun closeIMDbDatabase(){
        imDbDataBase.close()
    }

    @Test
    fun insertMoviesAndCompareThemWithRepo() = runTest {
        //Given a repository with top rated movies
        MoviesRepoMock.movies.forEach { imDbDataBase.imDbDao.insertMovie(it) }
        //When top rated movies are retrieved from db
        val loadedMovies = imDbDataBase.imDbDao.getTopRatedMovies()
        //Then assert the db movies match the repository with top rated movies
        loadedMovies.test {
            val movieList = awaitItem()
            assertThat(movieList, notNullValue())
            assertEquals(LIST_OF_MOVIES, movieList.size)
            assertEquals(MoviesRepoMock.movies, movieList)
            cancel()
        }
    }

    @Test
    fun deleteMoviesFromDB() = runTest {
        //Given a repository with top rated movies
        MoviesRepoMock.movies.forEach { imDbDataBase.imDbDao.insertMovie(it) }
        //When top rated movies are retrieved from db
        imDbDataBase.imDbDao.getTopRatedMovies()
        //Then assert the db movies where deleted from the db
        imDbDataBase.imDbDao.deleteAll()
        val loadedMovies = imDbDataBase.imDbDao.getTopRatedMovies()
        loadedMovies.test {
            val movieList = awaitItem()
            assertThat(movieList, notNullValue())
            assertEquals(0, movieList.size)
            cancel()
        }
    }

    companion object {
        const val LIST_OF_MOVIES = 8
    }
}