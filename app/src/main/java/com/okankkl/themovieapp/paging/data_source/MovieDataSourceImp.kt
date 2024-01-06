package com.okankkl.themovieapp.paging.data_source

import com.okankkl.themovieapp.api.TmdbApi
import com.okankkl.themovieapp.enum_sealed.Categories
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.response.ResponseDto

class MovieDataSourceImp(
    private val tmdbApi : TmdbApi
) : MovieDataSource
{
    override suspend fun getMovies(category: Categories, pageNumber: Int): ResponseDto<List<Movie>>
    {
        return tmdbApi.getMoviesPage(
            category =Categories.Popular.path,
            page = pageNumber
            )
    }
}