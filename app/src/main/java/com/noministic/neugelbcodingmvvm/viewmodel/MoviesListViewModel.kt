package com.noministic.neugelbcodingmvvm.viewmodel

import android.app.SearchManager
import android.database.MatrixCursor
import android.provider.BaseColumns
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.noministic.neugelbcodingmvvm.api.MoviesService
import com.noministic.neugelbcodingmvvm.model.Constants.API_KEY
import com.noministic.neugelbcodingmvvm.model.Movie
import com.noministic.neugelbcodingmvvm.model.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesListViewModel : ViewModel() {
    val moviesService = MoviesService()
    val movies = MutableLiveData<List<Movie>>()
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
        loading.value = true
        val moviesCall =
            moviesService.api.getTrendingMovies(API_KEY, pagesLoaded + 1)
        moviesCall?.enqueue(object : Callback<Result?> {
            override fun onResponse(call: Call<Result?>, response: Response<Result?>) {
                movies.value = response.body()?.movies
                pagesLoaded = response.body()?.page!!
                totalPages = response.body()?.totalPages!!
                loading.value = false
                loadingError.value = false
            }

            override fun onFailure(call: Call<Result?>, t: Throwable) {
                loading.value = false
                loadingError.value = true
            }
        })
    }

    fun searchMovie(query: String) {
        val searchCall = moviesService.api.searchMovie(API_KEY, query)
        searchCall?.enqueue(object : Callback<Result?> {
            override fun onResponse(call: Call<Result?>, response: Response<Result?>) {
                val mMovieModelList = response.body()?.movies
                suggestions.value?.clear()
                if (mMovieModelList?.size!! > 0) {
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

            override fun onFailure(call: Call<Result?>, t: Throwable) {
                Log.e("MoviesListViewModel", t.message.toString())
            }
        })
    }
}

