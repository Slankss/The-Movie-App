package com.okankkl.themovieapp.model

import androidx.room.ColumnInfo
import androidx.room.Ignore
import com.google.gson.annotations.SerializedName

class Movie(
    id : Int,
    backdropPath : String?,
    posterPath : String?,
    popularity : Double,
    voteAverage : Double,
    @SerializedName("title")
    override var title : String,
    category  : String = "",
    mediaType : String = ""
) : Display(id,backdropPath,posterPath,popularity,voteAverage,title,category,mediaType)
{

    @SerializedName("release_date")
    override var releaseDate : String = ""
    @Ignore
    var genres : List<Genres> = listOf()
    @Ignore
    var videos : Videos? = null
    @Ignore
    var revenue : Long = 0
    @Ignore
    var runtime = 0
    @Ignore
    var overview : String = ""


    constructor(id: Int,backdropPath: String?,posterPath: String?,popularity : Double,voteAverage: Double,title: String,
                releaseDate: String
    ) : this(id,backdropPath,posterPath,popularity,voteAverage,title){
        this.releaseDate = releaseDate
    }

    constructor(
        backdropPath: String?, genres : List<Genres>,id: Int,
        overview: String, popularity: Double, posterPath: String?,
        releaseDate: String, revenue : Long,runtime : Int, title: String,voteAverage: Double,
        videos : Videos
    )
            : this(id,backdropPath,posterPath,popularity,voteAverage,title)
    {
        this.genres = genres
        this.videos = videos
        this.revenue = revenue
        this.runtime = runtime
        this.overview = overview
        this.releaseDate = releaseDate
    }


}

