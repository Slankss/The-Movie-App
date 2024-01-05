package com.okankkl.themovieapp.model

import com.google.gson.annotations.SerializedName

data class CreatedBy(
    var id : Int,
    var name : String,
    @SerializedName("profile_path")
    var profilePath : String?
)