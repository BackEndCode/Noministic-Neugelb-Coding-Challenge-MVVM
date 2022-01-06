package com.noministic.neugelbcodingmvvm.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.noministic.neugelbcodingmvvm.model.MovieDetailModel

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieDetailModel)

    @Query("select * from movies")
    fun observeAllMovies(): LiveData<List<MovieDetailModel>>

    @Query("select * from movies where id=:id")
    suspend fun getMovieById(id: Int): MovieDetailModel?
}