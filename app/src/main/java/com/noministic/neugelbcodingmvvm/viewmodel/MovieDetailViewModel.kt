package com.noministic.neugelbcodingmvvm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noministic.neugelbcodingmvvm.data.DefaultShoppingRepository
import com.noministic.neugelbcodingmvvm.model.MovieDetailModel
import com.noministic.neugelbcodingmvvm.others.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val defaultShoppingRepository:
    DefaultShoppingRepository
) :
    ViewModel() {

    val movie = MutableLiveData<MovieDetailModel>()
    val loading = MutableLiveData<Boolean>()
    val loadingError = MutableLiveData<Boolean>()
    val moviesLoadingError = MutableLiveData<String>()
    fun getMovie(id: Int) {
        loadMovie(id)
    }

    fun addMovieToFavorite() {
        viewModelScope.launch {
            movie.value?.let { defaultShoppingRepository.insertMovie(it) }
        }
    }

    private fun loadMovie(id: Int) {
        viewModelScope.launch {
            val added = defaultShoppingRepository.addedToFavoriteOrNot(id)
            if (added != null) {
                loadingError.value = false
                loading.value = false
                movie.value = added!!
            } else {
                loading.postValue(true)
                val response = defaultShoppingRepository.getSingleMovie(id)
                when (response.status) {
                    Status.ERROR -> {
                        loading.postValue(false)
                        loadingError.postValue(true)
                        response.message?.let { moviesLoadingError.postValue(it) }

                    }
                    Status.LOADING -> {
                        loading.value = true
                    }
                    Status.SUCCESS -> {
                        loading.value = false
                        loadingError.value = false
                        response.data?.let { movie.value = it }
                    }
                }
            }
        }

    }
}
