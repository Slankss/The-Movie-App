package com.okankkl.themovieapp.repository

import com.okankkl.themovieapp.enum_sealed.Resources

interface MovieRepository
{
    suspend fun getMovieList() : Resources
    suspend fun getMovieDetail(id : Int) : Resources
}