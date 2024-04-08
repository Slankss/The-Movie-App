package com.okankkl.themovieapp.data.paging.data_source

import com.okankkl.themovieapp.data.remote.TmdbApi
import com.okankkl.themovieapp.data.remote.dto.MovieDto
import com.okankkl.themovieapp.data.remote.dto.TvSeriesDto
import com.okankkl.themovieapp.presentation.Categories
import com.okankkl.themovieapp.domain.model.response.ResponseDto

class ContentDataSourceImp(
    private val tmdbApi : TmdbApi
) : ContentDataSource
{
    override suspend fun getMovies(category: Categories, pageNumber: Int): ResponseDto<List<MovieDto>>
    {
        return tmdbApi.getMoviesPage(
            category = category.path,
            page = pageNumber
            )
    }

    override suspend fun getTvSeries(
        category: Categories,
        pageNumber: Int
    ): ResponseDto<List<TvSeriesDto>>
    {
        return tmdbApi.getTvSeriesPage(
            category = category.path,
            page = pageNumber
        )
    }
}