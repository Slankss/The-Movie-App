package com.okankkl.themovieapp.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class Company(
    var id : Int,
    @SerializedName("logo_path")
    var logoPath : String,
    var name : String,
    @SerializedName("origin_country")
    var originCountry : String
)