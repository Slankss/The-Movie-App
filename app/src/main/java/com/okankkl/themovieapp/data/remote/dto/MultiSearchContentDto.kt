package com.okankkl.themovieapp.data.remote.dto

import com.okankkl.themovieapp.domain.model.Content
import com.okankkl.themovieapp.domain.model.MultiSearchContent

data class MultiSearchContentDto(
    val adult: Boolean,
    val backdrop_path: String?,
    //val first_air_date: String,
    //val genre_ids: List<Int>,
    val id: Int,
    val media_type: String,
    val name: String,
    //val origin_country: List<String>,
    //val original_language: String,
    //val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val vote_average: Double,
    val vote_count: Int
)

fun MultiSearchContentDto.toContent() : Content {
    return Content(
        backdropPath = backdrop_path,
        posterPath = poster_path,
        id = id,
        mediaType = media_type
    )
}