package com.noministic.neugelbcodingmvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.noministic.neugelbcodingmvvm.api.MoviesService
import com.noministic.neugelbcodingmvvm.api.RequestInterface
import com.noministic.neugelbcodingmvvm.model.Constants
import com.noministic.neugelbcodingmvvm.model.MovieDetailModel
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val moviesService: MoviesService) : ViewModel() {
    val movie = MutableLiveData<MovieDetailModel>()
    val loading = MutableLiveData<Boolean>()
    val loadingError = MutableLiveData<Boolean>()
    fun getMovie(id: Int) {
        loadMovie(id)
    }

    private fun loadMovie(id: Int) {
        loading.value = true
        val call = moviesService.getSingleMovie(id, Constants.API_KEY)
        call?.enqueue(object : Callback<MovieDetailModel?> {
            override fun onResponse(
                call: Call<MovieDetailModel?>,
                response: Response<MovieDetailModel?>
            ) {
                movie.value = response.body()
                loading.value = false
                loadingError.value = false
            }

            override fun onFailure(call: Call<MovieDetailModel?>, t: Throwable) {
                loading.value = false
                loadingError.value = true
            }
        })
    }
}
