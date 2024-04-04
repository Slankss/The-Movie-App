package com.okankkl.themovieapp.domain.model.response

import com.okankkl.themovieapp.data.remote.dto.MultiSearchContentDto

data class MultiSearchContentResponse (
    val page: Int,
    val results: List<MultiSearchContentDto>,
    val totalPages: Int,
    val totalResults: Int
)