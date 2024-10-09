package com.okankkl.themovieapp.domain.repository

import com.okankkl.themovieapp.utils.Categories
import com.okankkl.themovieapp.utils.Resources
import com.okankkl.themovieapp.data.model.entity.MovieEntity
import com.okankkl.themovieapp.data.model.dto.Movie
import com.okankkl.themovieapp.data.model.dto.Review
import com.okankkl.themovieapp.data.response.ResponseDto

interface MoviesRepository {

    suspend fun getRemoteMovies(category: Categories, page: Int): Resources<ResponseDto<List<Movie>>>

    suspend fun getLocalMovies(): Resources<List<MovieEntity>>

    suspend fun addMoviesToLocal(movies: List<Movie>)

    suspend fun deleteLocalMovies()

    suspend fun getSimilarMovies(id: Int): Resources<ResponseDto<List<Movie>>>

    suspend fun getMovieDetail(id: Int): Resources<Movie>

    suspend fun getReviews(id: Int,page: Int): Resources<ResponseDto<List<Review>>>
}