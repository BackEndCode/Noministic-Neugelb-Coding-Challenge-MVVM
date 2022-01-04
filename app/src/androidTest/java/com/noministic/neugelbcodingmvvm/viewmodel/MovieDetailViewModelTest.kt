package com.noministic.neugelbcodingmvvm.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.noministic.neugelbcodingmvvm.data.DefaultShoppingRepository
import com.noministic.neugelbcodingmvvm.data.local.MoviesDao
import com.noministic.neugelbcodingmvvm.data.remote.MoviesAPI
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.*
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MovieDetailViewModelTest {
    @DelicateCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    lateinit var viewModel: MovieDetailViewModel
    val moviesDao: MoviesDao = mock(MoviesDao::class.java)
    val moviesAPI: MoviesAPI = mock(MoviesAPI::class.java)

    @Mock
    val apiEndPoint: DefaultShoppingRepository =
        DefaultShoppingRepository(moviesDao, moviesAPI)

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @DelicateCoroutinesApi
    @Before
    fun setup() {
        viewModel = MovieDetailViewModel(apiEndPoint)
        Dispatchers.setMain(mainThreadSurrogate)

    }

    @Test
    fun returnSuccessIfMovieFetched() {
        val movie_id = 72633
        viewModel.getMovie(movie_id)
        val value = viewModel.movie
        assertEquals(movie_id, value.value?.id)
    }

}