package com.okankkl.themovieapp.paging.data_source

import com.okankkl.themovieapp.enum_sealed.Categories
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.response.ResponseDto

interface MovieDataSource
{
    suspend fun getMovies(
        category: Categories,
        pageNumber : Int
    ) : ResponseDto<List<Movie>>
}