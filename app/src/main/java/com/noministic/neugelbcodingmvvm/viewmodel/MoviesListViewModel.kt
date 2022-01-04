package com.noministic.neugelbcodingmvvm.viewmodel

import android.app.SearchManager
import android.database.MatrixCursor
import android.provider.BaseColumns
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
class MoviesListViewModel @Inject constructor(private val defaultShoppingRepository: DefaultShoppingRepository) :
    ViewModel() {

    val movies = MutableLiveData<List<MovieDetailModel>>()
    val loading = MutableLiveData<Boolean>()
    val loadingError = MutableLiveData<Boolean>()
    var cursor: MatrixCursor? = null
    val suggestions = MutableLiveData<ArrayList<String>>()
    private var pagesLoaded = 1
    var totalPages = 0
    fun refresh() {
        //if total pages are less then 1 that means first page is not loaded yet
        if (totalPages < 1)
            loadMovies()
        else {
            if (pagesLoaded < totalPages) {
                loadMovies()
            }
        }
    }

    private fun loadMovies() {
        viewModelScope.launch {
            loading.postValue(true)
            val response = defaultShoppingRepository.getTrendingMovies(pageNum = pagesLoaded + 1)
            when (response.status) {
                Status.ERROR -> {
                    loading.value = false
                    loadingError.value = true
                }
                Status.LOADING -> {
                    loading.value = true
                }
                Status.SUCCESS -> {
                    loading.value = false
                    loadingError.value = false
                    response.data?.let {
                        movies.value = it.movies
                        pagesLoaded = it.page
                        totalPages = it.totalResults
                    }
                }
            }
        }
    }

    fun searchMovie(query: String) {
        viewModelScope.launch {
            val searchCall = defaultShoppingRepository.searchMovie(query = query)
            when (searchCall.status) {
                Status.ERROR -> {
                    loading.value = false
                    loadingError.value = true
                }
                Status.LOADING -> {
                    loading.value = true
                }
                Status.SUCCESS -> {
                    loading.value = false
                    loadingError.value = false
                    searchCall.data?.let {
                        val mMovieModelList = it.movies
                        suggestions.value?.clear()
                        if (mMovieModelList.size > 0) {
                            val columns = arrayOf(
                                BaseColumns._ID,
                                SearchManager.SUGGEST_COLUMN_TEXT_1,
                                SearchManager.SUGGEST_COLUMN_INTENT_DATA
                            )
                            cursor = MatrixCursor(columns)
                            val updatedSuggestions: ArrayList<String> = arrayListOf()
                            for (movie in mMovieModelList) {
                                updatedSuggestions.add(movie.title)
                                val tmp = arrayOf(
                                    movie.id.toString(),
                                    movie.title,
                                    movie.id.toString()
                                )
                                cursor!!.addRow(tmp)
                            }
                            suggestions.value = updatedSuggestions
                        }
                    }
                }
            }
        }
    }
}

