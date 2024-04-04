package com.okankkl.themovieapp.data.remote.dto

import com.okankkl.themovieapp.domain.model.response.MultiSearchContentResponse

data class MultiSearchContentResponseDto (
    val page: Int,
    val results: List<MultiSearchContentDto>,
    val total_pages: Int,
    val total_results: Int
)

fun MultiSearchContentResponseDto.toMultiSearchContentResponse()
    : MultiSearchContentResponse {
    return MultiSearchContentResponse(
        page =page,
        results = results,
        totalPages = total_pages,
        totalResults = total_results
    )
}