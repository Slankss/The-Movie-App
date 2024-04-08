package com.okankkl.themovieapp.domain.model

import com.okankkl.themovieapp.data.remote.dto.CreatedBy
import com.okankkl.themovieapp.data.remote.dto.Genre
import com.okankkl.themovieapp.data.remote.dto.Videos

class TvSeries(
    id : Int,
    backdropPath : String?,
    posterPath : String?,
    popularity : Double,
    voteAverage : Double,
    title : String,
    releaseDate : String,
    category : String,
    mediaType: String
) : Content(id,backdropPath,posterPath,title,mediaType,releaseDate,category,popularity,voteAverage)
{
    override var videos : Videos? = null
    var createdBy : List<CreatedBy> = listOf()
    var lastAirDate : String = ""
    var numberOfEpisodes : Int = 0
    var numberOfSeasons : Int = 0
    var overview : String = ""
    var genres : List<Genre> = listOf()

    constructor(id: Int,backdropPath: String?, posterPath: String?, popularity : Double,
                voteAverage: Double, title: String, releaseDate: String, category: String,
                mediaType: String, videos : Videos?, createdBy: List<CreatedBy>, lastAirDate : String,
                numberOfEpisodes : Int, numberOfSeasons : Int, overview: String, genres: List<Genre>,
        ) : this(id,backdropPath,posterPath,popularity,voteAverage,title,releaseDate,
        category,mediaType){

            this.overview = overview
            this.createdBy = createdBy
            this.genres = genres
            this.lastAirDate = lastAirDate
            this.numberOfEpisodes = numberOfEpisodes
            this.numberOfSeasons = numberOfSeasons
            this.videos = videos
        }
}