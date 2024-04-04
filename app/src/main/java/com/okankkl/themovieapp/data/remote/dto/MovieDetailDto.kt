package com.okankkl.themovieapp.data.remote.dto

import com.okankkl.themovieapp.domain.model.Movie

data class MovieDetailDto(
    val adult: Boolean,
    val backdrop_path: String?,
    val belongs_to_collection: Any,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val production_companies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>,
    val release_date: String,
    val revenue: Long,
    val runtime: Int,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val videos: Videos?,
    val vote_average: Double,
    val vote_count: Int
)

fun MovieDetailDto.toMovie() : Movie {
    return Movie(
        id = id,
        backdropPath = backdrop_path,
        posterPath = poster_path,
        popularity = popularity,
        voteAverage = vote_average,
        title = title,
        releaseDate = release_date,
        category = "",
        mediaType = "movie",
        videos = videos,
        overview = overview,
        genres = genres,
        revenue = revenue,
        runtime = runtime
    )
}