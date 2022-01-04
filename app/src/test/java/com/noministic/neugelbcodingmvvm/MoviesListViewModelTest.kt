package com.noministic.neugelbcodingmvvm


import com.google.common.truth.Truth.assertThat
import com.noministic.neugelbcodingmvvm.model.Movie
import com.noministic.neugelbcodingmvvm.model.Result
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class MoviesListViewModelTest {
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getMoviesSuccessTest() {
        val listofInts = listOf(1, 3, 9, 16, 25, 36)
        val movie = Movie(
            true, "skldfj", listofInts, 13, "sfs", "sfs", "sfs",
            "sfs", 23423.0, "sfs", "sfs", "sfs", true, 234.0, 23
        )

        //val result = Result(1, listOf(movie), 34, 26)
        //assertThat(result.movies.size).isEqualTo(1)
    }
}