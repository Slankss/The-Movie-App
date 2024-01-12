package com.okankkl.themovieapp.paging.data_source

import com.okankkl.themovieapp.enum_sealed.Categories
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.model.TvSeries
import com.okankkl.themovieapp.response.ResponseDto

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