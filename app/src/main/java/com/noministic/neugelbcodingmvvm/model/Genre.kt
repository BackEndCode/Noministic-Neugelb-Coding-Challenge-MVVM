package com.noministic.neugelbcodingmvvm.model


import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.noministic.neugelbcodingmvvm.others.Constants

@Entity(tableName = Constants.GENRE_TABLE_NAME)
data class Genre(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)