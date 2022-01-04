package com.noministic.neugelbcodingmvvm.model


import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.noministic.neugelbcodingmvvm.others.Constants

@Entity(tableName = Constants.SPOKEN_LANGUAGES_TABLE_NAME)
data class SpokenLanguage(
    @SerializedName("english_name")
    val englishName: String,
    @SerializedName("iso_639_1")
    val iso6391: String,
    @SerializedName("name")
    val name: String
)