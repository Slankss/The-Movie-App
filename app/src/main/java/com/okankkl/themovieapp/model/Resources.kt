package com.okankkl.themovieapp.model

sealed class Resources
{
    data class Success(val movieList : List<Movie>) : Resources()
    object Loading : Resources()
    data class Failed(val errorMsg : String) : Resources()
}