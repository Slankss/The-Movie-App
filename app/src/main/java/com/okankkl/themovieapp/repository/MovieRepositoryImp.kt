package com.okankkl.themovieapp.repository

import com.okankkl.themovieapp.api.MovieApi
import com.okankkl.themovieapp.enum_sealed.MovieListType
import com.okankkl.themovieapp.enum_sealed.Resources
import javax.inject.Inject

class MovieRepositoryImp
    @Inject
    constructor(var movieApi : MovieApi) : MovieRepository
{
    override suspend fun getMovieList(movieListType: MovieListType): Resources
    {
        return try {
            val response = when(movieListType){
                MovieListType.Popular -> movieApi.getPopularMovies()
                MovieListType.TopRated -> movieApi.getTopRatedMovies()
                MovieListType.NowPlaying -> movieApi.getNowPlayingMovies()
            }

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

    override suspend fun getMovieDetail(id: Int): Resources
    {
        return try
        {
            val response = movieApi.getMovie(id = id)
            if(response.isSuccessful){
                response.body()?.let {
                    Resources.Success(data = it)
                } ?: Resources.Failed(errorMsg = "Error")
            }
            else{
                Resources.Failed(errorMsg = "Error")
            }
        } catch (e : Exception){
            Resources.Failed(e.message!!)
        }
    }

    override suspend fun getSimilarMovies(id: Int): Resources
    {
        return try {
            val response = movieApi.getSimilarMovies(id = 695721)

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
}