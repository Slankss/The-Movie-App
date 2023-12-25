package com.okankkl.themovieapp.enum_sealed

sealed class Resources
{
    data class Success(val data : Any) : Resources()
    object Loading : Resources()
    data class Failed(val errorMsg : String) : Resources()
}