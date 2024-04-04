package com.okankkl.themovieapp.presentation.home_screen

data class ContentState(
    var is_loading : Boolean = false,
    var data : ContentList? = null,
    var message : String = ""
)
