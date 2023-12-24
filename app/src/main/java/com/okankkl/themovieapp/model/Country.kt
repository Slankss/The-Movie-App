package com.okankkl.themovieapp.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class Country(
    @SerializedName("iso_3166_1")
    var iso : String,
    var name : String
)