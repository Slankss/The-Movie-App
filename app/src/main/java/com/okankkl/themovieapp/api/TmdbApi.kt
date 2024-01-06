package com.okankkl.themovieapp.api

import android.provider.ContactsContract.Data
import com.okankkl.themovieapp.enum_sealed.DataType
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.response.MovieResponse
import com.okankkl.themovieapp.response.ResponseDto
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

    @GET("/3/{type}/{category}")
    suspend fun getDisplayList(
        @Path("type") type : String,
        @Path("category") category : String,
        @Query("page") page : Int,
        @Query("api_key") apiKey : String = API_KEY
    ) : Response<MovieResponse>

    @GET("/3/{type}/{id}")
    suspend fun getDisplayDetail(
        @Path("type") type : String,
        @Path("id") id : Int,
        @Query("api_key") apiKey : String = API_KEY,
        @Query("append_to_response") appendToVideos: String = "videos"
    ) : Response<Movie>

    @GET("/3/{type}/{id}/similar")
    suspend fun getSimilarDisplayList(
        @Path("type") type : String,
        @Path("id") id : Int,
        @Query("api_key") apiKey : String = API_KEY
    ) : Response<MovieResponse>

    @GET("/3/trending/{type}/week")
    suspend fun getTrendingdDisplayList(
        @Path("type") type: String,
        @Query("api_key") apiKey : String = API_KEY
    ) : Response<MovieResponse>



}