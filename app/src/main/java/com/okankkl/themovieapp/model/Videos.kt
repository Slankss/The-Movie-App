package com.okankkl.themovieapp.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Videos(
    var results : List<Video>
)

@Serializable
data class Video(

    var name : String,
    @SerializedName("published_at")
    var publishedAt : String,
    var site : String,
    var size : Int,
    var type : String,
    var official : Boolean,
    var id : String,
    var key : String
)
