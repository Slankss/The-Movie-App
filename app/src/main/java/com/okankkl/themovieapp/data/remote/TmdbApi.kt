package com.okankkl.themovieapp.data.remote

import com.okankkl.themovieapp.domain.model.Movie
import com.okankkl.themovieapp.domain.model.TvSeries
import com.okankkl.themovieapp.domain.model.response.MovieResponse
import com.okankkl.themovieapp.domain.model.response.ResponseDto
import com.okankkl.themovieapp.domain.model.response.ContentResponse
import com.okankkl.themovieapp.domain.model.response.TvSeriesResponse
import com.okankkl.themovieapp.common.Constants.API_KEY
import com.okankkl.themovieapp.data.remote.dto.MovieDetailDto
import com.okankkl.themovieapp.data.remote.dto.MovieDto
import com.okankkl.themovieapp.data.remote.dto.MovieResponseDto
import com.okankkl.themovieapp.data.remote.dto.MultiSearchContentResponseDto
import com.okankkl.themovieapp.data.remote.dto.TvSeriesDetailDto
import com.okankkl.themovieapp.data.remote.dto.TvSeriesDto
import com.okankkl.themovieapp.data.remote.dto.TvSeriesResponseDto
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
    ) : ResponseDto<List<MovieDto>>
    @GET("/3/movie/{category}")
    suspend fun getMovies(
        @Path("category") category : String,
        @Query("page") page : Int,
        @Query("api_key") apiKey : String = API_KEY
    ) : MovieResponseDto

    @GET("/3/movie/{id}")
    suspend fun getMovie(
        @Path("id") id : Int,
        @Query("api_key") apiKey : String = API_KEY,
        @Query("append_to_response") appendToVideos: String = "videos"
    ) : MovieDetailDto


    @GET("/3/movie/{id}/similar")
    suspend fun getSimilarMovies(
        @Path("id") id : Int,
        @Query("api_key") apiKey : String = API_KEY
    ) : MovieResponseDto
    
    @GET("/3/trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey : String = API_KEY
    ) : MovieResponseDto

    @GET("/3/tv/{category}")
    suspend fun getTvSeriesList(
        @Path("category") category : String,
        @Query("page") page : Int,
        @Query("api_key") apiKey : String = API_KEY
    ) : TvSeriesResponseDto


    @GET("/3/trending/tv/week")
    suspend fun getTrendingTvSeries(
        @Query("api_key") apiKey : String = API_KEY
    ) : TvSeriesResponseDto

    @GET("/3/tv/{id}")
    suspend fun getTvSeries(
        @Path("id") id : Int,
        @Query("api_key") apiKey : String = API_KEY,
        @Query("append_to_response") appendToVideos: String = "videos"
    ) : TvSeriesDetailDto

    @GET("/3/tv/{id}/recommendations")
    suspend fun getSimilarTvSeries(
        @Path("id") id : Int,
        @Query("api_key") apiKey : String = API_KEY
    ) : TvSeriesResponseDto

    @GET("/3/tv/{category}")
    suspend fun getTvSeriesPage(
        @Path("category") category : String,
        @Query("page") page : Int,
        @Query("api_key") apiKey : String = API_KEY
    ) : ResponseDto<List<TvSeriesDto>>

    @GET("/3/search/multi")
    suspend fun getSearch(
        @Query("query") query: String,
        @Query("api_key") apiKey : String = API_KEY
    ) : MultiSearchContentResponseDto


}