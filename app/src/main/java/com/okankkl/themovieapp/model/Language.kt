package com.okankkl.themovieapp.model

import com.google.gson.annotations.SerializedName

data class Language(
    @SerializedName("english_name")
    var englishName : String,
    var name : String
)