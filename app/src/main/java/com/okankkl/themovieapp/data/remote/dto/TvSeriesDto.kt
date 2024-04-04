package com.okankkl.themovieapp.data.remote.dto

import com.okankkl.themovieapp.domain.model.Content

data class TvSeriesDto(
    val adult: Boolean,
    val backdrop_path: String,
    val first_air_date: String,
    val genre_ids: List<Int>,
    val id: Int,
    val name: String,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val vote_average: Double,
    val vote_count: Int
)

fun TvSeriesDto.toContent() : Content {
    return Content(
        id = id,
        backdropPath = backdrop_path,
        posterPath = poster_path,
        popularity = popularity,
        voteAverage = vote_average,
        title = name,
        releaseDate = first_air_date ?: "",
        category = "",
        mediaType = "tv"
    )
}