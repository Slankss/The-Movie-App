package com.okankkl.themovieapp.repository

import com.okankkl.themovieapp.enum_sealed.Categories
import com.okankkl.themovieapp.enum_sealed.Resources


interface Repository
{
    suspend fun getMovieList(moviesType: Categories,page : Int) : Resources
    suspend fun getMovieDetail(id : Int) : Resources
    suspend fun getSimilarMovies(id : Int) : Resources

    suspend fun getTvSeriesList(tvSeriesType: Categories,page : Int) : Resources
    suspend fun getTvSeriesDetail(id : Int) : Resources
    suspend fun getSimilarTvSeries(id : Int) : Resources
}