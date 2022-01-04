package com.noministic.neugelbcodingmvvm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.noministic.neugelbcodingmvvm.model.Movie
import com.noministic.neugelbcodingmvvm.model.MovieDetailModel

@Database(entities = [MovieDetailModel::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}