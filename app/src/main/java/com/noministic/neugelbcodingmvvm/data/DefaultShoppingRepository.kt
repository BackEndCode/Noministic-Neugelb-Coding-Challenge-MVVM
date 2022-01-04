package com.noministic.neugelbcodingmvvm.data

import androidx.lifecycle.LiveData
import com.noministic.neugelbcodingmvvm.data.local.MoviesDao
import com.noministic.neugelbcodingmvvm.data.remote.MoviesAPI
import com.noministic.neugelbcodingmvvm.model.MovieDetailModel
import com.noministic.neugelbcodingmvvm.model.Result
import com.noministic.neugelbcodingmvvm.others.Resource
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
    private val moviesDao: MoviesDao,
    private val moviesAPI: MoviesAPI
) : MoviesRepository {
    override suspend fun insertMovie(movie: MovieDetailModel) {
        moviesDao.insertMovie(movie)
    }

    override fun observeAllMovies(): LiveData<List<MovieDetailModel>> {
        return moviesDao.observeAllMovies()
    }

    override suspend fun addedToFavoriteOrNot(id: Int): MovieDetailModel? {
        return moviesDao.addedToFavoriteOrNot(id)
    }

    override suspend fun getSingleMovie(movie_id: Int): Resource<MovieDetailModel> {
        return try {
            val response = moviesAPI.getSingleMovie(movie_id)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: return Resource.error("unknow error", null)
            } else {
                return Resource.error("unknow error", null)
            }
        } catch (e: Exception) {
            return Resource.error("an unknow error occured", null)
        }
    }

    override suspend fun searchMovie(query: String): Resource<Result?> {
        return try {
            val response = moviesAPI.searchMovie(query)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: return Resource.error("unknow error", null)
            } else {
                return Resource.error("unknow error", null)
            }
        } catch (e: Exception) {
            return Resource.error("an unknow error occured", null)
        }
    }

    override suspend fun getTrendingMovies(pageNum: Int): Resource<Result?> {
        return try {
            val response = moviesAPI.getTrendingMovies(pageNum)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: return Resource.error("unknow error", null)
            } else {
                return Resource.error("unknow error", null)
            }
        } catch (e: Exception) {
            return Resource.error("an unknow error occured", null)
        }
    }

}