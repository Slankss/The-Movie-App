package com.okankkl.themovieapp.paging.data_source

import com.okankkl.themovieapp.api.TmdbApi
import com.okankkl.themovieapp.enum_sealed.Categories
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.model.TvSeries
import com.okankkl.themovieapp.response.ResponseDto

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