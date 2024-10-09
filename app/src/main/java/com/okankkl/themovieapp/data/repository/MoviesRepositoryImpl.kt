package com.okankkl.themovieapp.data.repository

import com.okankkl.themovieapp.data.datasource.movies.local.MoviesLocalDataSource
import com.okankkl.themovieapp.data.datasource.movies.remote.MoviesRemoteDataSource
import com.okankkl.themovieapp.domain.repository.MoviesRepository
import com.okankkl.themovieapp.utils.Categories
import com.okankkl.themovieapp.utils.Resources
import com.okankkl.themovieapp.data.model.entity.MovieEntity
import com.okankkl.themovieapp.data.model.dto.Movie
import com.okankkl.themovieapp.data.model.dto.Review
import com.okankkl.themovieapp.data.response.ResponseDto
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val localDataSource: MoviesLocalDataSource,
    private val remoteDataSource: MoviesRemoteDataSource
) : MoviesRepository {
    override suspend fun getRemoteMovies(category: Categories, page: Int): Resources<ResponseDto<List<Movie>>> {
        return if (category == Categories.Trending){
            remoteDataSource.getTrendingMovies()
        } else {
            remoteDataSource.getMovies(category,page)
        }
    }

    override suspend fun getLocalMovies(): Resources<List<MovieEntity>> {
        return localDataSource.getMovies()
    }

    override suspend fun addMoviesToLocal(movies: List<Movie>) {
        localDataSource.addMovies(movies)
    }

    override suspend fun deleteLocalMovies() {
        localDataSource.clearMovies()
    }

    override suspend fun getSimilarMovies(id: Int): Resources<ResponseDto<List<Movie>>> {
        return remoteDataSource.getSimilarMovies(id)
    }

    override suspend fun getMovieDetail(id: Int): Resources<Movie> {
        return remoteDataSource.getMovieDetail(id)
    }

    override suspend fun getReviews(id: Int, page: Int): Resources<ResponseDto<List<Review>>> {
        return remoteDataSource.getReviews(id, page)
    }
}