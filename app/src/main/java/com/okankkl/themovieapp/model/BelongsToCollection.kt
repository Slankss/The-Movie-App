package com.okankkl.themovieapp.model

import com.google.gson.annotations.SerializedName

data class BelongsToCollection(
    var id : Int,
    var name : String,
    @SerializedName("poster_path")
    var posterPath : String,
    @SerializedName("backdrop_path")
    var backdropPath : String
)