package com.okankkl.themovieapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Display")
open class Content(
    @PrimaryKey
    var id : Int,
    @ColumnInfo(defaultValue = "")
    var backdropPath : String?,
    @ColumnInfo(defaultValue = "")
    var posterPath : String?,
    var popularity : Double,
    var voteAverage : Double,
    var title : String,
    var releaseDate : String,
    @ColumnInfo(defaultValue = "")
    var category  : String,
    @ColumnInfo(defaultValue = "")
    var mediaType : String?
){



}
