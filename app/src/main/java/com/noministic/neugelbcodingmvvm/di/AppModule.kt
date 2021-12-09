package com.noministic.neugelbcodingmvvm.di

import com.noministic.neugelbcodingmvvm.api.MoviesService
import com.noministic.neugelbcodingmvvm.api.RequestInterface
import com.noministic.neugelbcodingmvvm.model.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRequestInterface(): RequestInterface = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(RequestInterface::class.java)

    @Singleton
    @Provides
    fun provideMoviesService(requestInterface: RequestInterface): MoviesService =
        MoviesService(requestInterface)
}