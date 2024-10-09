package com.okankkl.themovieapp.data.model.dto

import com.google.gson.annotations.SerializedName
import com.okankkl.themovieapp.data.response.ResponseDto
import com.okankkl.themovieapp.domain.model.Content

data class TvSeries(
    val id: Int? = null,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    val genres: List<Genre>? = null,
    @SerializedName("name")
    val title:String? = null,
    val category: String? = null,
    @SerializedName("first_air_date")
    val releaseDate: String? = null,
    val video: Boolean? = null,
    val videos: Videos? = null,
    val voteCount: Int? = null,
    val tagline: String? = null,
    val adult: Boolean? = null,
    val status: String? = null,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>? = null,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>? = null,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry>? = null,
    val overview: String? = null,
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
    @SerializedName("original_language")
    val originalLanguage: String? = null,
    val homepage: String? = null,
    @SerializedName("created_by")
    val createdBy: List<CreatedBy>? = null,
    @SerializedName("episode_run_time")
    val episodeRunTime: List<Int>? = null,
    @SerializedName("in_production")
    val inProduction: Boolean? = null,
    val languages: List<String>? = null,
    @SerializedName("last_air_date")
    val lastAirDate: String? = null,
    @SerializedName("last_episode_to_air")
    val lastEpisodeToAir: LastEpisodeToAir? = null,
    val networks: List<Network>? = null,
    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int? = null,
    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int? = null,
    @SerializedName("origin_country")
    val originCountry: List<String>? = null,
    @SerializedName("original_name")
    val originalName: String? = null,
    val popularity: Double? = null,
    val seasons: List<Season>? = null,
    val credits: Credits? = null,
    val images: Images? = null,
    val similar: ResponseDto<List<TvSeries>>? = null
)