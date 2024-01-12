package com.okankkl.themovieapp.model

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.okankkl.themovieapp.enum_sealed.Categories

@Entity
data class Movie(
    @ColumnInfo(defaultValue = "")
    @SerializedName("backdrop_path")
    var backdropPath : String?,
    @PrimaryKey
    var id : Int,
    var popularity : Double,
    @ColumnInfo(defaultValue = "")
    @SerializedName("poster_path")
    var posterPath : String?,
    @SerializedName("release_date")
    var releaseDate : String,
    var title : String,
    @SerializedName("vote_average")
    var voteAverage : Double,
    @ColumnInfo(defaultValue = "")
    var category  : String = " "
){
    @Ignore
    var genres : List<Genres> = listOf()
    @Ignore
    var videos : Videos? = null
    @Ignore
    var revenue : Int = 0
    @Ignore
    var runtime = 0
    @Ignore
    var overview : String = ""


    constructor(
        backdropPath: String?, genres : List<Genres>,id: Int,
        overview: String, popularity: Double, posterPath: String?,
        releaseDate: String, revenue : Int,runtime : Int, title: String,voteAverage: Double,
        videos : Videos
    )
            : this(backdropPath, id, popularity, posterPath, releaseDate, title, voteAverage)
    {
        this.genres = genres
        this.videos = videos
        this.revenue = revenue
        this.runtime = runtime
        this.overview = overview

    }



}
