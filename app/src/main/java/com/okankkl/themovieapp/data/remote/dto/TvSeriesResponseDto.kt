package com.okankkl.themovieapp.data.remote.dto

import com.okankkl.themovieapp.domain.model.response.ContentResponse

data class TvSeriesResponseDto(
    val page: Int,
    val results: List<TvSeriesDto>,
    val total_pages: Int,
    val total_results: Int
)

fun TvSeriesResponseDto.toContentResponse() : ContentResponse {
    return ContentResponse(
        page = page,
        results = results.map { it.toContent() },
        totalPages = total_pages,
        totalResults = total_results
    )
}