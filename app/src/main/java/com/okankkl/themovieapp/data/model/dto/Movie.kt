package com.okankkl.themovieapp.data.model.dto

import com.google.gson.annotations.SerializedName
import com.okankkl.themovieapp.data.response.ResponseDto
import com.okankkl.themovieapp.domain.model.Content

data class Movie(
    val id: Int? = null,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    val genres: List<Genre>? = null,
    val category: String? = null,
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
    @SerializedName("release_date")
    val releaseDate: String? = null,
    val title: String? = null,
    val budget: Int? = null,
    @SerializedName("imdb_id")
    val imdbId: String? = null,
    @SerializedName("original_title")
    val originalTitle: String? = null,
    val popularity: Double? = null,
    val revenue: Long? = null,
    val runtime: Int? = null,
    val credits: Credits? = null,
    val images: Images? = null,
    val similar: ResponseDto<List<Movie>>? = null
)