package com.noministic.neugelbcodingmvvm.di

import android.content.Context
import androidx.room.Room
import com.noministic.neugelbcodingmvvm.data.DefaultShoppingRepository
import com.noministic.neugelbcodingmvvm.data.MoviesRepository
import com.noministic.neugelbcodingmvvm.data.local.MoviesDao
import com.noministic.neugelbcodingmvvm.data.local.MoviesDatabase
import com.noministic.neugelbcodingmvvm.data.remote.MoviesAPI
import com.noministic.neugelbcodingmvvm.others.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesMoviesDatabase(@ApplicationContext context: Context): MoviesDao =
        Room.databaseBuilder(
            context,
            MoviesDatabase::class.java, Constants.MOVIES_DATABASE_NAME
        ).build().moviesDao()

    @Singleton
    @Provides
    fun providesDefaultShoppingRepository(
        movies: MoviesDao,
        moviesAPI: MoviesAPI
    ): MoviesRepository =
        DefaultShoppingRepository(movies, moviesAPI)

    @Singleton
    @Provides
    fun provideRequestInterface(): MoviesAPI = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(MoviesAPI::class.java)
}