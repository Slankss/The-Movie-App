package com.okankkl.themovieapp.domain.model.response

import com.okankkl.themovieapp.domain.model.Content


data class ContentResponse(
    var page : Int,
    var results : List<Content>,
    var totalPages : Int,
    var totalResults : Int
)


