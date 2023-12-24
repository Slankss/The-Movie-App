package com.okankkl.themovieapp.model

sealed class Resources
{
    data class Success(val data : Any) : Resources()
    object Loading : Resources()
    data class Failed(val errorMsg : String) : Resources()
}