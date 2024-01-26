package com.okankkl.themovieapp.api

import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.model.TvSeries
import com.okankkl.themovieapp.response.MovieResponse
import com.okankkl.themovieapp.response.ResponseDto
import com.okankkl.themovieapp.response.DisplayResponse
import com.okankkl.themovieapp.response.TvSeriesResponse
import com.okankkl.themovieapp.util.Util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi
{
    @GET("/3/movie/{category}")
    suspend fun getMoviesPage(
            @Path("category") category : String,
            @Query("page") page : Int,
            @Query("api_key") apiKey : String = API_KEY
    ) : ResponseDto<List<Movie>>
    @GET("/3/movie/{category}")
    suspend fun getMovies(
        @Path("category") category : String,
        @Query("page") page : Int,
        @Query("api_key") apiKey : String = API_KEY
    ) : Response<MovieResponse>

    @GET("/3/movie/{id}")
    suspend fun getMovie(
        @Path("id") id : Int,
        @Query("api_key") apiKey : String = API_KEY,
        @Query("append_to_response") appendToVideos: String = "videos"
    ) : Response<Movie>


    @GET("/3/movie/{id}/similar")
    suspend fun getSimilarMovies(
        @Path("id") id : Int,
        @Query("api_key") apiKey : String = API_KEY
    ) : Response<MovieResponse>
    
    @GET("/3/trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey : String = API_KEY
    ) : Response<MovieResponse>

    @GET("/3/tv/{category}")
    suspend fun getTvSeries(
        @Path("category") category : String,
        @Query("page") page : Int,
        @Query("api_key") apiKey : String = API_KEY
    ) : Response<TvSeriesResponse>


    @GET("/3/trending/tv/week")
    suspend fun getTrendingTvSeries(
        @Query("api_key") apiKey : String = API_KEY
    ) : Response<TvSeriesResponse>

    @GET("/3/tv/{id}")
    suspend fun getTvSeries(
        @Path("id") id : Int,
        @Query("api_key") apiKey : String = API_KEY,
        @Query("append_to_response") appendToVideos: String = "videos"
    ) : Response<TvSeries>

    @GET("/3/tv/{id}/recommendations")
    suspend fun getSimilarTvSeries(
        @Path("id") id : Int,
        @Query("api_key") apiKey : String = API_KEY
    ) : Response<TvSeriesResponse>

    @GET("/3/tv/{category}")
    suspend fun getTvSeriesPage(
        @Path("category") category : String,
        @Query("page") page : Int,
        @Query("api_key") apiKey : String = API_KEY
    ) : ResponseDto<List<TvSeries>>

    @GET("/3/search/multi")
    suspend fun getSearch(
        @Query("query") query: String,
        @Query("api_key") apiKey : String = API_KEY
    ) : Response<DisplayResponse>


}