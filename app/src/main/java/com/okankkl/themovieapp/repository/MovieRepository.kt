package com.okankkl.themovieapp.repository

import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.model.Resources
import com.okankkl.themovieapp.response.MovieResponse

interface MovieRepository
{
    fun getMovieList() : Resources
    fun getMovieDetail(id : Int) : Movie?
}