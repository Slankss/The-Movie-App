package com.okankkl.themovieapp.data.remote.dto

import com.okankkl.themovieapp.domain.model.response.ContentResponse

data class MovieResponseDto(
    val page: Int,
    val results: List<MovieDto>,
    val total_pages: Int,
    val total_results: Int
)

fun MovieResponseDto.toContentResponse() : ContentResponse{
    return ContentResponse(
        page = page,
        results = results.map { it.toContent() },
        totalPages = total_pages,
        totalResults = total_results
    )
}