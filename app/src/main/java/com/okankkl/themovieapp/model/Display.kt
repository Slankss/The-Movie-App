package com.okankkl.themovieapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("Display")
open class Display(
    @PrimaryKey
    open var id : Int,
    @ColumnInfo(defaultValue = "")
    @SerializedName("backdrop_path")
    open var backdropPath : String?,
    @ColumnInfo(defaultValue = "")
    @SerializedName("poster_path")
    open var posterPath : String?,
    open var popularity : Double,
    @SerializedName("vote_average")
    open var voteAverage : Double,
    open var title : String,
    @ColumnInfo(defaultValue = "")
    open var category  : String = " ",
    @ColumnInfo(defaultValue = "")
    open var mediaType : String? = " "
){

    @Ignore
    @ColumnInfo(defaultValue = "")
    open var releaseDate : String = ""


}
