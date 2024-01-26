package com.okankkl.themovieapp.model

import androidx.room.Ignore
import com.google.gson.annotations.SerializedName

class Movie(
    id : Int,
    backdropPath : String?,
    posterPath : String?,
    popularity : Double,
    voteAverage : Double,
    @SerializedName("title")
    override var en_title : String,
    @SerializedName("release_date")
    override var releaseDate: String,
    category  : String = "",
    mediaType : String = "movie"
) : Display(id,backdropPath,posterPath,popularity,voteAverage,en_title,releaseDate,category,mediaType)
{

    @Ignore
    var revenue : Long = 0
    @Ignore
    var runtime = 0

    constructor(
        backdropPath: String?, genres : List<Genres>,id: Int,
        overview: String, popularity: Double, posterPath: String?,
        releaseDate: String, revenue : Long,runtime : Int, title: String,voteAverage: Double,
        videos : Videos
    )
            : this(id,backdropPath,posterPath,popularity,voteAverage,title,releaseDate)
    {
        this.genres = genres
        this.videos = videos
        this.revenue = revenue
        this.runtime = runtime
        this.overview = overview
    }

}

