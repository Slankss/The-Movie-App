package com.okankkl.themovieapp.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.okankkl.themovieapp.enum_sealed.Categories
import com.okankkl.themovieapp.enum_sealed.Resources
import com.okankkl.themovieapp.model.Movie
import kotlinx.coroutines.flow.Flow


interface Repository
{
    suspend fun getMovieList(moviesType: Categories,page : Int) : Resources
    suspend fun getMovieDetail(id : Int) : Resources
    suspend fun getSimilarMovies(id : Int) : Resources
    
    suspend fun getMoviesPage(category : Categories) : Flow<PagingData<Movie>>

    suspend fun getTvSeriesList(tvSeriesType: Categories,page : Int) : Resources
    suspend fun getTvSeriesDetail(id : Int) : Resources
    suspend fun getSimilarTvSeries(id : Int) : Resources
}