package com.okankkl.themovieapp.domain.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class MultiSearchContent(
    var backdropPath : String?,
    //var name : String,
    var posterPath: String?,
    var id : Int,
    var mediaType : String
){

}
