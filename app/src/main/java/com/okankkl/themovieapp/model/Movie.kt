package com.okankkl.themovieapp.model

import com.google.gson.annotations.SerializedName


data class Movie(
    var adult : String,
    @SerializedName("backdrop_path")
    var backdropPath : String,
    var id : Int,
    @SerializedName("original_language")
    var originalLanguage : String,
    @SerializedName("original_title")
    var originalTitle : String,
    var overview : String,
    var popularity : Double,
    @SerializedName("poster_path")
    var posterPath : String,
    @SerializedName("release_date")
    var releaseDate : String,
    var title : String,
    var video : Boolean,
    @SerializedName("vote_average")
    var voteAverage : Double,
    @SerializedName("vote_count")
    var voteCount : Int
){

    @SerializedName("genre_ids")
    var genreIds : List<Int> = listOf<Int>()
    @SerializedName("belongs_to_collection")
    var belongsToCollection : BelongsToCollection? = null
    var budget : Int = 0
    var genres : List<Genres> = listOf<Genres>()
    var homepage : String = ""
    @SerializedName("imdb_id")
    var imdbId : String = ""
    @SerializedName("production_companies")
    var productionCompanies : List<Company> = listOf<Company>()
    @SerializedName("production_countries")
    var productionCountries : List<Country> = listOf<Country>()
    var status : String = ""
    var tagline : String = ""
    var videos : Videos? = null
    var revenue : Int = 0
    var runtime = 0
    @SerializedName("spoken_languages")
    var spokenLanguages : List<Language> = listOf<Language>()


    constructor(
        adult: String,backdropPath: String,genreIds : List<Int>,id: Int,originalLanguage: String,
        originalTitle: String,overview: String,popularity: Double,posterPath: String,
        releaseDate: String, title: String,video: Boolean,voteAverage: Double,voteCount: Int,
                )
            : this(adult, backdropPath, id, originalLanguage, originalTitle, overview, popularity,
        posterPath, releaseDate, title, video, voteAverage, voteCount)
    {
        this.genreIds = genreIds
    }


    constructor(
        adult: String,backdropPath: String,belongsToCollection : BelongsToCollection?,budget : Int,
        genres : List<Genres>,homepage : String,id: Int,imdbId : String,originalLanguage: String,
        originalTitle: String, overview: String, popularity: Double, posterPath: String,
        productionCompanies : List<Company>,productionCountries : List<Country>,releaseDate: String,
        revenue : Int,runtime : Int,spokenLanguages : List<Language>,status: String, tagline : String,
        title: String,video: Boolean,voteAverage: Double, voteCount: Int,videos : Videos

        )
            : this(adult, backdropPath, id, originalLanguage, originalTitle, overview, popularity,
        posterPath, releaseDate, title, video, voteAverage, voteCount)
    {
        this.genres = genres
        this.belongsToCollection = belongsToCollection
        this.budget = budget
        this.homepage = homepage
        this.imdbId = imdbId
        this.productionCompanies = productionCompanies
        this.productionCountries = productionCountries
        this.status = status
        this.tagline = tagline
        this.videos = videos
        this.revenue = revenue
        this.runtime = runtime
        this.spokenLanguages = spokenLanguages
    }



}