package com.okankkl.themovieapp.paging.data_source

import com.okankkl.themovieapp.presentation.Categories
import com.okankkl.themovieapp.domain.model.Movie
import com.okankkl.themovieapp.domain.model.TvSeries
import com.okankkl.themovieapp.domain.model.response.ResponseDto

interface DataSources
{
    suspend fun getMovies(
        category: Categories,
        pageNumber : Int
    ) : ResponseDto<List<Movie>>

    suspend fun getTvSeries(
        category: Categories,
        pageNumber: Int
    ) : ResponseDto<List<TvSeries>>
}