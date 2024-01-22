package com.okankkl.themovieapp.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class Search(
    @ColumnInfo(defaultValue = "")
    @SerializedName("backdrop_path")
    var backdropPath : String?,
    var name : String,
    @ColumnInfo(defaultValue = "")
    @SerializedName("poster_path")
    var posterPath: String?,
    var id : Int,
    @SerializedName("media_type")
    var mediaType : String

){

}
