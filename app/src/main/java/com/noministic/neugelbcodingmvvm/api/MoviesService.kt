package com.noministic.neugelbcodingmvvm.api

class MoviesService(private val apiService: RequestInterface) {
    fun getSingleMovie(id: Int, api: String) = apiService.getSingleMovie(id, api)
    fun getTrendingMovies(api: String, pageNum: Int) = apiService.getTrendingMovies(api, pageNum)
    fun searchMovie(api: String, query: String) = apiService.searchMovie(api, query)

}