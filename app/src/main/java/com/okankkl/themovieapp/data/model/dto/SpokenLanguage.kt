package com.okankkl.themovieapp.data.model.dto

import com.google.gson.annotations.SerializedName

data class SpokenLanguage(
    @SerializedName("english_name")
    val englishName: String? = null,
    val iso_639_1: String? = null,
    val name: String? = null
)