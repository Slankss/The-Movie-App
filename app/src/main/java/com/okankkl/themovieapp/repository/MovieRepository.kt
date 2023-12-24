package com.okankkl.themovieapp.repository

import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.model.Resources
import com.okankkl.themovieapp.response.MovieResponse

interface MovieRepository
{
    suspend fun getMovieList() : Resources
    suspend fun getMovieDetail(id : Int) : Resources
}