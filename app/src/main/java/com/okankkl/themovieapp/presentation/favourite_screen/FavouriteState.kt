package com.okankkl.themovieapp.presentation.favourite_screen

import com.okankkl.themovieapp.domain.model.Favourite

data class FavouriteState(
    var is_loading : Boolean = false,
    var data : List<Favourite>? = null,
    var message : String = ""
)