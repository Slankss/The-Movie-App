package com.okankkl.themovieapp.paging.data_source

import com.okankkl.themovieapp.data.remote.TmdbApi
import com.okankkl.themovieapp.presentation.Categories
import com.okankkl.themovieapp.domain.model.Movie
import com.okankkl.themovieapp.domain.model.TvSeries
import com.okankkl.themovieapp.domain.model.response.ResponseDto

class DataSourcesImp(
    private val tmdbApi : TmdbApi
) : DataSources
{
    override suspend fun getMovies(category: Categories, pageNumber: Int): ResponseDto<List<Movie>>
    {
        return tmdbApi.getMoviesPage(
            category = category.path,
            page = pageNumber
            )
    }

    override suspend fun getTvSeries(
        category: Categories,
        pageNumber: Int
    ): ResponseDto<List<TvSeries>>
    {
        return tmdbApi.getTvSeriesPage(
            category = category.path,
            page = pageNumber
        )
    }
}