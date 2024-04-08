package com.okankkl.themovieapp.data.paging.data_source

import com.okankkl.themovieapp.data.remote.dto.MovieDto
import com.okankkl.themovieapp.data.remote.dto.TvSeriesDto
import com.okankkl.themovieapp.presentation.Categories
import com.okankkl.themovieapp.domain.model.response.ResponseDto

interface ContentDataSource
{
    suspend fun getMovies(
        category: Categories,
        pageNumber : Int
    ) : ResponseDto<List<MovieDto>>

    suspend fun getTvSeries(
        category: Categories,
        pageNumber: Int
    ) : ResponseDto<List<TvSeriesDto>>
}