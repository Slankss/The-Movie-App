package com.okankkl.themovieapp.api

import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.response.MovieResponse
import com.okankkl.themovieapp.util.Util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi
{

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey : String = API_KEY
    ) : Response<MovieResponse>

    @GET("/3/movie/{id}")
    suspend fun getMovie(
        @Path("id") id : Int,
        @Query("api_key") apiKey : String = API_KEY,
        @Query("append_to_response") appendToVideos: String = "videos"
    ) : Response<Movie>

    @GET("/3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey : String = API_KEY
    ) : Response<MovieResponse>

    @GET("/3/movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey : String = API_KEY
    ) : Response<MovieResponse>

    @GET("/3/movie/{id}/similar")
    suspend fun getSimilarMovies(
        @Path("id") id : Int,
        @Query("api_key") apiKey : String = API_KEY
    ) : Response<MovieResponse>



}