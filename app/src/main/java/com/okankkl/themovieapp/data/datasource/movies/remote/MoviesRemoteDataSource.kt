package com.okankkl.themovieapp.data.datasource.movies.remote

import com.okankkl.themovieapp.utils.Categories
import com.okankkl.themovieapp.utils.Resources
import com.okankkl.themovieapp.data.model.dto.Movie
import com.okankkl.themovieapp.data.model.dto.Review
import com.okankkl.themovieapp.data.response.ResponseDto

interface MoviesRemoteDataSource {

    suspend fun getMovies(category: Categories, page: Int) : Resources<ResponseDto<List<Movie>>>

    suspend fun getTrendingMovies() : Resources<ResponseDto<List<Movie>>>

    suspend fun getMovieDetail(id : Int) : Resources<Movie>

    suspend fun getSimilarMovies(id : Int) : Resources<ResponseDto<List<Movie>>>

    suspend fun getReviews(id : Int, page: Int) : Resources<ResponseDto<List<Review>>>
}