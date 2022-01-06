package com.noministic.neugelbcodingmvvm.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.noministic.neugelbcodingmvvm.data.DefaultShoppingRepository
import com.noministic.neugelbcodingmvvm.data.local.MoviesDao
import com.noministic.neugelbcodingmvvm.data.local.MoviesDatabase
import com.noministic.neugelbcodingmvvm.data.remote.MoviesAPI
import com.noministic.neugelbcodingmvvm.model.MovieDetailModel
import com.noministic.neugelbcodingmvvm.others.Constants
import com.noministic.neugelbcodingmvvm.others.Status
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.*
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MovieDetailViewModelTest {
    @DelicateCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    lateinit var moviesDao: MoviesDao
    lateinit var moviesAPI: MoviesAPI
    lateinit var defaultShoppingRepository: DefaultShoppingRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @DelicateCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        val context = ApplicationProvider.getApplicationContext<Context>()
        moviesDao = Room.inMemoryDatabaseBuilder(context, MoviesDatabase::class.java)
            .build().moviesDao()
        moviesAPI = Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(MoviesAPI::class.java)
        defaultShoppingRepository = DefaultShoppingRepository(moviesDao, moviesAPI)
    }

    @Test
    fun should_return_success_movie_exists() {
        runBlocking {
            val movie_id = 729120
            val response = defaultShoppingRepository.getSingleMovie(movie_id)
            assertEquals(Status.SUCCESS, response.status)
        }
    }

    @Test
    fun should_return_error_movie_doesnt_exists() {
        runBlocking {
            val movie_id = 72631343
            val response = defaultShoppingRepository.getSingleMovie(movie_id)
            assertEquals(Status.ERROR, response.status)
        }
    }

    @Test
    fun should_not_return_empty_list_trending_movies_exists() {
        runBlocking {
            val response = defaultShoppingRepository.getTrendingMovies(1)
            assertTrue(response.data!!.movies.isNotEmpty())
        }
    }

    @Test
    fun inserted_movie_fetched_movie_should_be_same() {
        runBlocking {
            val movie = MovieDetailModel(
                true,
                "skldfj",
                1300,
                "sfs",
                234,
                "sfs",
                "sfs",
                "testing movie",
                "sfskdjfks.,ertes",
                3.5,
                "sfs",
                "23/23/5343",
                234342,
                23,
                "published",
                "sfsx",
                "testing movieee paiiii",
                true,
                2.3,
                2222
            )

            defaultShoppingRepository.insertMovie(movie)
            val varifyInsert = defaultShoppingRepository.getMovieById(movie.id)
            assertEquals(movie, varifyInsert)
        }
    }

}