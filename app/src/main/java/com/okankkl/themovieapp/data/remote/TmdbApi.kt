package com.okankkl.themovieapp.data.remote

import com.okankkl.themovieapp.data.response.ResponseDto
import com.okankkl.themovieapp.utils.Constants.API_KEY
import com.okankkl.themovieapp.data.model.dto.Movie
import com.okankkl.themovieapp.data.model.dto.Person
import com.okankkl.themovieapp.data.model.dto.Review
import com.okankkl.themovieapp.data.model.dto.TvSeries
import com.okankkl.themovieapp.domain.model.Content
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi
{
    @GET("movie/{category}")
    suspend fun getMovies(
        @Path("category") category : String,
        @Query("page") page : Int,
        @Query("api_key") apiKey : String = API_KEY
    ) : Response<ResponseDto<List<Movie>>>

    @GET("movie/{id}")
    suspend fun getMovie(
        @Path("id") id : Int,
        @Query("api_key") apiKey : String = API_KEY,
        @Query("append_to_response") appendToVideos: String = "videos,images,credits,similar"
    ) : Response<Movie>

    @GET("movie/{id}/similar")
    suspend fun getSimilarMovies(
        @Path("id") id : Int,
        @Query("api_key") apiKey : String = API_KEY
    ) : Response<ResponseDto<List<Movie>>>
    
    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey : String = API_KEY
    ) : Response<ResponseDto<List<Movie>>>

    @GET("tv/{category}")
    suspend fun getTvSeriesList(
        @Path("category") category : String,
        @Query("page") page : Int,
        @Query("api_key") apiKey : String = API_KEY
    ) : Response<ResponseDto<List<TvSeries>>>


    @GET("trending/tv/week")
    suspend fun getTrendingTvSeries(
        @Query("api_key") apiKey : String = API_KEY
    ) : Response<ResponseDto<List<TvSeries>>>

    @GET("tv/{id}")
    suspend fun getTvSeries(
        @Path("id") id : Int,
        @Query("api_key") apiKey : String = API_KEY,
        @Query("append_to_response") appendToVideos: String = "videos,images,credits,similar"
    ) : Response<TvSeries>

    @GET("tv/{id}/recommendations")
    suspend fun getSimilarTvSeries(
        @Path("id") id : Int,
        @Query("api_key") apiKey : String = API_KEY
    ) : Response<ResponseDto<List<TvSeries>>>

    @GET("search/multi")
    suspend fun getSearch(
        @Query("query") query: String,
        @Query("api_key") apiKey : String = API_KEY,
        @Query("page") page: Int
    ) : Response<ResponseDto<List<Content>>>

    @GET("movie/{id}/reviews")
    suspend fun getMovieReviews(
        @Path("id") id : Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey : String = API_KEY
    ) : Response<ResponseDto<List<Review>>>

    @GET("tv/{id}/reviews")
    suspend fun getTvSeriesReviews(
        @Path("id") id : Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey : String = API_KEY,
    ) : Response<ResponseDto<List<Review>>>

    @GET("person/{id}")
    suspend fun getPersonDetail(
        @Path("id") id : Int,
        @Query("api_key") apikey : String = API_KEY
    ) : Response<Person>

}