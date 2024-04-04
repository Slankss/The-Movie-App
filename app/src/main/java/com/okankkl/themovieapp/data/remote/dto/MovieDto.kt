package com.okankkl.themovieapp.data.remote.dto

import com.okankkl.themovieapp.domain.model.Content

data class MovieDto(
    val adult: Boolean,
    val backdrop_path: String?,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String?,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

fun MovieDto.toContent() : Content {
    return Content(
        id = id,
        backdropPath = backdrop_path,
        posterPath = poster_path,
        popularity = popularity,
        voteAverage = vote_average,
        title = title,
        releaseDate = release_date ?: "",
        category = "",
        mediaType = "movie"
    )
}