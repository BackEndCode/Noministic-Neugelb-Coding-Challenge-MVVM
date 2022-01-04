package com.noministic.neugelbcodingmvvm.model


import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.noministic.neugelbcodingmvvm.others.Constants

@Entity(tableName = Constants.PRODUCTION_COMPANY_TABLE_NAME)
data class ProductionCompany(
    @SerializedName("id")
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("origin_country")
    val originCountry: String
)