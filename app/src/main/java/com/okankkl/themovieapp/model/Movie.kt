package com.okankkl.themovieapp.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Movie")
data class Movie(
    @SerializedName("backdrop_path")
    var backdropPath : String?,
    var id : Int,
    var popularity : Double,
    @SerializedName("poster_path")
    var posterPath : String?,
    @SerializedName("release_date")
    var releaseDate : String,
    var title : String,
    @SerializedName("vote_average")
    var voteAverage : Double
){
    var genres : List<Genres> = listOf()
    @SerializedName("imdb_id")
    var imdbId : String = ""
    var videos : Videos? = null
    var revenue : Int = 0
    var runtime = 0
    var overview : String = ""

    constructor(
       backdropPath: String?, genres : List<Genres>,id: Int,imdbId : String,
       overview: String, popularity: Double, posterPath: String?,
       releaseDate: String, revenue : Int,runtime : Int,
       title: String,voteAverage: Double, videos : Videos
    )
            : this(backdropPath, id, popularity, posterPath, releaseDate, title, voteAverage)
    {
        this.genres = genres
        this.imdbId = imdbId

        this.videos = videos
        this.revenue = revenue
        this.runtime = runtime
        this.overview = overview

    }



}