package com.okankkl.themovieapp.model

import com.google.gson.annotations.SerializedName

data class TvSeries(
    @SerializedName("backdrop_path")
    var backdropPath : String?,
    @SerializedName("first_air_date")
    var firstAirDate : String,
    var id : Int,
    var overview : String,
    @SerializedName("poster_path")
    var posterPath : String?,
    var name : String,
    @SerializedName("vote_average")
    var voteAverage : Double
){

    @SerializedName("created_by")
    var createdBy : List<CreatedBy> = listOf()
    @SerializedName("episode_run_time")
    var episodeRuntime : List<Int> = listOf()
    var genres : List<Genres> = listOf()
    @SerializedName("last_air_date")
    var lastAirDate : String = ""
    @SerializedName("number_of_episodes")
    var numberOfEpisodes : Int = 0
    @SerializedName("number_of_seasons")
    var numberOfSeasons : Int = 0
    var videos : Videos? = null


    constructor(backdropPath: String?,firstAirDate: String,id: Int,overview: String,
                posterPath: String?,name: String,voteAverage: Double,
                createdBy: List<CreatedBy>, episodeRuntime : List<Int>,genres: List<Genres>,lastAirDate : String,
                numberOfEpisodes : Int,numberOfSeasons : Int,videos : Videos?

        ) : this(backdropPath, firstAirDate, id, overview, posterPath, name, voteAverage){

            this.createdBy = createdBy
            this.episodeRuntime = episodeRuntime
            this.genres = genres
            this.lastAirDate = lastAirDate
            this.numberOfEpisodes = numberOfEpisodes
            this.numberOfSeasons = numberOfSeasons
            this.videos = videos
        }


}