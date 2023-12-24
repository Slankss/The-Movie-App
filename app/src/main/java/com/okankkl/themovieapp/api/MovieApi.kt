package com.okankkl.themovieapp.api

import com.okankkl.themovieapp.response.MovieResponse
import com.okankkl.themovieapp.util.Util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi
{

    @GET("/3/movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey : String = API_KEY
    ) : Response<MovieResponse>

}