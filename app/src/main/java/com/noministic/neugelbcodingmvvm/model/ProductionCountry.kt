package com.noministic.neugelbcodingmvvm.model


import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.noministic.neugelbcodingmvvm.others.Constants

@Entity(tableName = Constants.PRODUCTION_COUNTRY_TABLE_NAME)
data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso31661: String,
    @SerializedName("name")
    val name: String
)