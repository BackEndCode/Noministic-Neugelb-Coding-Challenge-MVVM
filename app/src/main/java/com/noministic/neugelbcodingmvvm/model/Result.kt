package com.noministic.neugelbcodingmvvm.model


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val movies: List<MovieDetailModel>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)