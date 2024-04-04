package com.okankkl.themovieapp.presentation.content_detail_screen

import com.okankkl.themovieapp.domain.model.Content

data class ContentDetailState(
    var is_loading : Boolean = false,
    var data : Content? = null,
    var message : String = ""
)
