package com.okankkl.themovieapp.presentation.home_screen
import com.okankkl.themovieapp.domain.model.Content

data class ContentList(
    var popular : List<Content> = emptyList(),
    var top_rated : List<Content> = emptyList(),
    var trending : List<Content> = emptyList(),
    var now_playing : List<Content> = emptyList(),
)
