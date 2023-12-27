package com.okankkl.themovieapp.repository

import com.okankkl.themovieapp.enum_sealed.MovieListType
import com.okankkl.themovieapp.enum_sealed.Resources

interface MovieRepository
{
    suspend fun getMovieList(movieListType: MovieListType) : Resources
    suspend fun getMovieDetail(id : Int) : Resources
    suspend fun getSimilarMovies(id : Int) : Resources
}