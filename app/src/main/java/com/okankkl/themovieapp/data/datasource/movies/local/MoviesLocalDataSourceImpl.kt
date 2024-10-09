package com.okankkl.themovieapp.data.datasource.movies.local

import com.okankkl.themovieapp.utils.Resources
import com.okankkl.themovieapp.utils.roomCall
import com.okankkl.themovieapp.data.local.database.LocalDb
import com.okankkl.themovieapp.data.model.entity.MovieEntity
import com.okankkl.themovieapp.data.mapper.toMovieEntity
import com.okankkl.themovieapp.data.model.dto.Movie
import javax.inject.Inject

class MoviesLocalDataSourceImpl @Inject constructor(
    private val db: LocalDb
) : MoviesLocalDataSource {

    override suspend fun getMovies(): Resources<List<MovieEntity>> {
        return roomCall { db.getMovies() }
    }

    override suspend fun addMovies(movieDetails: List<Movie>) {
        db.addMovies(
            movieDetails.map { it.toMovieEntity() }
        )
    }

    override suspend fun clearMovies() {
        db.deleteMovies()
    }
}