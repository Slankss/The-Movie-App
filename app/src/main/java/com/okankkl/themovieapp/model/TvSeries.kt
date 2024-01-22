package com.okankkl.themovieapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "TvSeries")
data class TvSeries(
    @ColumnInfo(defaultValue = "")
    @SerializedName("backdrop_path")
    var backdropPath : String?,
    @SerializedName("first_air_date")
    var firstAirDate : String,
    @PrimaryKey
    var id : Int,

    @ColumnInfo(defaultValue = "")
    @SerializedName("poster_path")
    var posterPath : String?,
    var name : String,
    @SerializedName("vote_average")
    var voteAverage : Double,
    @ColumnInfo(defaultValue = "")
    var category : String = ""
){
    @Ignore
    @SerializedName("created_by")
    var createdBy : List<CreatedBy> = listOf()

    @Ignore
    @SerializedName("episode_run_time")
    var episodeRuntime : List<Int> = listOf()

    @Ignore
    var genres : List<Genres> = listOf()

    @Ignore
    @SerializedName("last_air_date")
    var lastAirDate : String = ""

    @Ignore
    @SerializedName("number_of_episodes")
    var numberOfEpisodes : Int = 0

    @Ignore
    var overview : String = ""

    @Ignore
    @SerializedName("number_of_seasons")
    var numberOfSeasons : Int = 0

    @Ignore
    var videos : Videos? = null

    constructor(backdropPath: String?,firstAirDate: String,id: Int,overview: String,
                posterPath: String?,name: String,voteAverage: Double,
                createdBy: List<CreatedBy>, episodeRuntime : List<Int>,genres: List<Genres>,lastAirDate : String,
                numberOfEpisodes : Int,numberOfSeasons : Int,videos : Videos?

        ) : this(backdropPath, firstAirDate, id, posterPath, name, voteAverage){

            this.overview = overview
            this.createdBy = createdBy
            this.episodeRuntime = episodeRuntime
            this.genres = genres
            this.lastAirDate = lastAirDate
            this.numberOfEpisodes = numberOfEpisodes
            this.numberOfSeasons = numberOfSeasons
            this.videos = videos
        }
}