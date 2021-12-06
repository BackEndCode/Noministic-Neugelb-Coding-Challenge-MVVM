package com.noministic.neugelbcodingmvvm.api

import com.noministic.neugelbcodingmvvm.model.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesService {
    val api: RequestInterface = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(RequestInterface::class.java)
}