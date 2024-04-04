package com.okankkl.themovieapp.domain.repository

import androidx.paging.PagingData
import com.okankkl.themovieapp.presentation.Categories
import com.okankkl.themovieapp.domain.model.Movie
import com.okankkl.themovieapp.domain.model.TvSeries
import com.okankkl.themovieapp.domain.model.response.ContentResponse
import com.okankkl.themovieapp.domain.model.response.MultiSearchContentResponse
import kotlinx.coroutines.flow.Flow

interface ApiRepository
{
    suspend fun getMovies(category: Categories, page : Int) : ContentResponse
    suspend fun getMovieDetail(id : Int) : Movie
    suspend fun getSimilarMovies(id : Int) : ContentResponse
    suspend fun getMoviesPage(category : Categories) : Flow<PagingData<Movie>>
    suspend fun getTvSeries(category: Categories, page : Int): ContentResponse
    suspend fun getTvSeriesDetail(id : Int) : TvSeries
    suspend fun getSimilarTvSeries(id : Int) : ContentResponse
    suspend fun getTvSeriesPage(category: Categories): Flow<PagingData<TvSeries>>
    suspend fun search(query : String) : MultiSearchContentResponse
}