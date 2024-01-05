package com.okankkl.themovieapp.data_source

import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.response.MovieResponse
import com.okankkl.themovieapp.response.ResponseDto

interface movieDataSource
{
    suspend fun getMovies(
        apikey : String,
        pageNumber : Int
    ) : ResponseDto<List<Movie>>
}