package com.okankkl.themovieapp.domain.model

import com.okankkl.themovieapp.data.remote.dto.Genre
import com.okankkl.themovieapp.data.remote.dto.Videos

class Movie(
    id : Int,
    backdropPath : String?,
    posterPath : String?,
    popularity : Double,
    voteAverage : Double,
    title : String,
    releaseDate: String,
    category  : String,
    mediaType : String
) : Content(id,backdropPath,posterPath,title,mediaType,releaseDate,category,popularity,voteAverage)
{

    override var videos : Videos? = null
    var overview : String = ""
    var genres : List<Genre> = listOf()
    var revenue : Long = 0
    var runtime = 0

    constructor(
        id: Int, backdropPath: String?, posterPath: String?, popularity: Double,
        voteAverage: Double, title: String, releaseDate: String, category: String,
        mediaType: String,  videos : Videos?, overview: String,
        genres : List<Genre>, revenue : Long, runtime : Int,
    ) : this(id,backdropPath,posterPath,popularity,voteAverage,title,releaseDate,
        category,mediaType)
    {
        this.genres = genres
        this.videos = videos
        this.revenue = revenue
        this.runtime = runtime
        this.overview = overview
    }

}

