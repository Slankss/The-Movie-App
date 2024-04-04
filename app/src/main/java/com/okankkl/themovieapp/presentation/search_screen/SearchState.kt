package com.okankkl.themovieapp.presentation.search_screen

import com.okankkl.themovieapp.domain.model.MultiSearchContent

data class SearchState(
    var is_loading : Boolean = false,
    var data : List<MultiSearchContent> = emptyList(),
    var message : String = ""
)
