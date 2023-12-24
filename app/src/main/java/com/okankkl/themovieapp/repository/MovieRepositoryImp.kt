package com.okankkl.themovieapp.repository

import com.okankkl.themovieapp.api.MovieApi
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.model.Resources
import com.okankkl.themovieapp.response.MovieResponse
import javax.inject.Inject

class MovieRepositoryImp
    @Inject
    constructor(var movieApi : MovieApi) : MovieRepository
{
    override fun getMovieList(): Resources
    {
        return try {
            val response = movieApi.getPopularMovies()
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resources.Success(it.results)
                } ?: Resources.Failed("Error")
            }
            else{
               Resources.Failed("Error")
            }
        } catch (e:Exception){
            Resources.Failed(e.localizedMessage!!)
        }
    }

    override fun getMovieDetail(id: Int): Movie?
    {
        return null
    }
}