package com.noministic.neugelbcodingmvvm.data

import androidx.lifecycle.LiveData
import com.noministic.neugelbcodingmvvm.model.MovieDetailModel
import com.noministic.neugelbcodingmvvm.model.Result
import com.noministic.neugelbcodingmvvm.others.Resource
import retrofit2.Response

interface MoviesRepository {

    suspend fun insertMovie(movie: MovieDetailModel)

    fun observeAllMovies(): LiveData<List<MovieDetailModel>>

    suspend fun getMovieById(id: Int): MovieDetailModel?

    suspend fun getSingleMovie(movie_id:Int): Resource<MovieDetailModel>

    suspend fun searchMovie(query:String):Resource<Result?>

    suspend fun getTrendingMovies(pageNum:Int):Resource<Result?>

}