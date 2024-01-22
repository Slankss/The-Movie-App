package com.okankkl.themovieapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

class TvSeries(
    id : Int,
    backdropPath : String?,
    posterPath : String?,
    popularity : Double,
    voteAverage : Double,
    @SerializedName("name")
    override var title : String,
    category : String = "",
    mediaType: String = ""
) : Display(id,backdropPath,posterPath,popularity,voteAverage,title,category,mediaType)
{
    @SerializedName("first_air_date")
    override var releaseDate : String = ""
    @Ignore
    @SerializedName("created_by")
    var createdBy : List<CreatedBy> = listOf()
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


    constructor(id: Int,backdropPath: String?,posterPath: String?,popularity : Double,voteAverage: Double,title: String,
        firstAirDate : String
    ) : this(id,backdropPath,posterPath,popularity,voteAverage,title){
        this.releaseDate = firstAirDate
    }

    constructor(backdropPath: String?, firstAirDate: String,id: Int,overview: String,
                posterPath: String?,title: String,voteAverage: Double,
                createdBy: List<CreatedBy>,genres: List<Genres>,lastAirDate : String,
                numberOfEpisodes : Int,numberOfSeasons : Int,videos : Videos?,popularity : Double

        ) : this(id,backdropPath,posterPath,popularity,voteAverage,title){

            this.overview = overview
            this.createdBy = createdBy
            this.genres = genres
            this.lastAirDate = lastAirDate
            this.numberOfEpisodes = numberOfEpisodes
            this.numberOfSeasons = numberOfSeasons
            this.videos = videos
            this.releaseDate = firstAirDate
        }
}