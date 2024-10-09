package com.okankkl.themovieapp.data.datasource.movies.remote

import com.okankkl.themovieapp.data.remote.TmdbApi
import com.okankkl.themovieapp.utils.Categories
import com.okankkl.themovieapp.utils.Resources
import com.okankkl.themovieapp.utils.apiCall
import com.okankkl.themovieapp.data.model.dto.Movie
import com.okankkl.themovieapp.data.model.dto.Review
import com.okankkl.themovieapp.data.response.ResponseDto
import javax.inject.Inject

class MoviesRemoteDataSourceImpl @Inject constructor(
    private val api: TmdbApi
) : MoviesRemoteDataSource {

    override suspend fun getMovies(category: Categories, page: Int): Resources<ResponseDto<List<Movie>>> {
        return apiCall { api.getMovies(category.path, page) }
    }

    override suspend fun getTrendingMovies(): Resources<ResponseDto<List<Movie>>> {
        return apiCall { api.getTrendingMovies() }
    }

    override suspend fun getMovieDetail(id: Int): Resources<Movie> {
        return apiCall { api.getMovie(id) }
    }

    override suspend fun getSimilarMovies(id: Int): Resources<ResponseDto<List<Movie>>> {
        return apiCall { api.getSimilarMovies(id) }
    }

    override suspend fun getReviews(id: Int, page: Int): Resources<ResponseDto<List<Review>>> {
        return apiCall { api.getMovieReviews(id,page) }
    }
}