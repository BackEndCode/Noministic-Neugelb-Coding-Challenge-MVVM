package com.noministic.neugelbcodingmvvm.data.remote

import com.noministic.neugelbcodingmvvm.BuildConfig
import com.noministic.neugelbcodingmvvm.model.MovieDetailModel
import com.noministic.neugelbcodingmvvm.model.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesAPI {
    //List of trending movies api call
    @GET("/3/trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("page") pageNum: Int,
        @Query("api_key") apikey: String=BuildConfig.API_KEY,
        ): Response<Result>

    //Movie search
    @GET("/3/search/movie")
    suspend fun searchMovie(
        @Query("query") query: String?,
        @Query("api_key") apikey: String=BuildConfig.API_KEY
    ): Response<Result>

    //Single movie api call
    @GET("/3/movie/{movie_id}")
    suspend fun getSingleMovie(
        @Path("movie_id") id: Int,
        @Query("api_key") apikey: String=BuildConfig.API_KEY
    ): Response<MovieDetailModel>

}