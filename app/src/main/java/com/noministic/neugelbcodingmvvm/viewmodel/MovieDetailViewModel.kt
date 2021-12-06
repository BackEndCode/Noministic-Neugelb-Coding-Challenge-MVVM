package com.noministic.neugelbcodingmvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.noministic.neugelbcodingmvvm.model.MovieDetailModel
import com.noministic.neugelbcodingmvvm.api.MoviesService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailViewModel : ViewModel() {
    val moviesService = MoviesService()
    val movie = MutableLiveData<MovieDetailModel>()
    val loading = MutableLiveData<Boolean>()
    val loadingError = MutableLiveData<Boolean>()
    fun getMovie(id: Int) {
        loadMovie(id)
    }

    private fun loadMovie(id: Int) {
        loading.value = true
        val call =
            moviesService.api.getSingleMovie(id, "af34851455aab38957c96592591b38c0")
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
