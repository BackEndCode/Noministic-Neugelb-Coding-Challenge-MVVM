package com.noministic.neugelbcodingmvvm.api

import com.noministic.neugelbcodingmvvm.model.MovieDetailModel
import com.noministic.neugelbcodingmvvm.model.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RequestInterface {
    //List of trending movies api call
    @GET("/3/trending/movie/week")
    fun getTrendingMovies(
        @Query("api_key") apikey: String?,
        @Query("page") pageNum: Int
    ): Call<Result?>?

    //Movie search
    @GET("/3/search/movie")
    fun searchMovie(
        @Query("api_key") apikey: String?,
        @Query("query") query: String?
    ): Call<Result?>?

    //Single movie api call
    @GET("/3/movie/{movie_id}")
    fun getSingleMovie(
        @Path("movie_id") id: Int,
        @Query("api_key") apikey: String?
    ): Call<MovieDetailModel?>?

}