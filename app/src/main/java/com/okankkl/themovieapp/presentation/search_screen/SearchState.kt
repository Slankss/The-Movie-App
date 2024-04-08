package com.okankkl.themovieapp.presentation.search_screen
import com.okankkl.themovieapp.domain.model.Content

data class SearchState(
    var is_loading : Boolean = false,
    var data : List<Content> = emptyList(),
    var message : String = ""
)
