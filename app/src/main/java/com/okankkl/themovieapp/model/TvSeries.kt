package com.okankkl.themovieapp.model

import androidx.room.Ignore
import com.google.gson.annotations.SerializedName

class TvSeries(
    id : Int,
    backdropPath : String?,
    posterPath : String?,
    popularity : Double,
    voteAverage : Double,
    @SerializedName("name")
    override var en_title : String,
    @SerializedName("first_air_date")
    override var releaseDate : String,
    category : String = "",
    mediaType: String = "tv"
) : Display(id,backdropPath,posterPath,popularity,voteAverage,en_title,releaseDate,category,mediaType)
{

    @Ignore
    @SerializedName("created_by")
    var createdBy : List<CreatedBy> = listOf()
    @Ignore
    @SerializedName("last_air_date")
    var lastAirDate : String = ""
    @Ignore
    @SerializedName("number_of_episodes")
    var numberOfEpisodes : Int = 0
    @Ignore
    @SerializedName("number_of_seasons")
    var numberOfSeasons : Int = 0

    constructor(backdropPath: String?, releaseDate: String,id: Int,overview: String,
                posterPath: String?,title: String,voteAverage: Double,
                createdBy: List<CreatedBy>,genres: List<Genres>,lastAirDate : String,
                numberOfEpisodes : Int,numberOfSeasons : Int,videos : Videos?,popularity : Double

        ) : this(id,backdropPath,posterPath,popularity,voteAverage,title,releaseDate){

            this.overview = overview
            this.createdBy = createdBy
            this.genres = genres
            this.lastAirDate = lastAirDate
            this.numberOfEpisodes = numberOfEpisodes
            this.numberOfSeasons = numberOfSeasons
            this.videos = videos

        }
}