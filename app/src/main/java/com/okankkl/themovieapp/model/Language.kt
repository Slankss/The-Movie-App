package com.okankkl.themovieapp.model

import com.google.gson.annotations.SerializedName

data class Language(
    @SerializedName("english_name")
    var englishName : String,
    @SerializedName("iso_639_1")
    var iso : String,
    var name : String
)