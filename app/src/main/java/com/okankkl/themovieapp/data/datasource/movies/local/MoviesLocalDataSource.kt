package com.okankkl.themovieapp.data.datasource.movies.local

import com.okankkl.themovieapp.utils.Resources
import com.okankkl.themovieapp.data.model.dto.Movie
import com.okankkl.themovieapp.data.model.entity.MovieEntity

interface MoviesLocalDataSource {

    suspend fun getMovies() : Resources<List<MovieEntity>>

    suspend fun addMovies(movieDetails : List<Movie>)

    suspend fun clearMovies()
}