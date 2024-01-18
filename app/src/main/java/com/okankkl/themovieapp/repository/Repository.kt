package com.okankkl.themovieapp.repository

import androidx.paging.PagingData
import com.okankkl.themovieapp.enum_sealed.Categories
import com.okankkl.themovieapp.enum_sealed.DisplayType
import com.okankkl.themovieapp.enum_sealed.Resources
import com.okankkl.themovieapp.model.Favourite
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.model.MovieEntity
import com.okankkl.themovieapp.model.TvSeries
import kotlinx.coroutines.flow.Flow


interface Repository
{
    suspend fun getMovieListFromAPI(moviesType: Categories, page : Int) : List<Movie>
    suspend fun getMovieDetailFromAPI(id : Int) : Resources
    suspend fun getSimilarMoviesFromAPI(id : Int) : Resources

    
    suspend fun getMoviesPage(category : Categories) : Flow<PagingData<Movie>>

    suspend fun getTvSeriesList(tvSeriesType: Categories,page : Int) : List<TvSeries>
    suspend fun getTvSeriesDetail(id : Int) : Resources
    suspend fun getSimilarTvSeries(id : Int) : Resources

    suspend fun getTvSeriesPage(category: Categories): Flow<PagingData<TvSeries>>

    suspend fun getMovieListFromRoom(category : Categories) : List<Movie>
    suspend fun addMovieListToRoom(movieList : List<Movie>)
    suspend fun deleteMovieListFromRoom()

    suspend fun getTvSeriesListFromRoom(category: Categories) : List<TvSeries>
    suspend fun addTvSeriesListToRoom(tvSeriesList : List<TvSeries>)
    suspend fun deleteTvSeriesListFromRoom()

    suspend fun getFavourites(displayType: DisplayType) : List<Favourite>
    suspend fun getFavourite(contentId : Int) : Favourite?
    suspend fun addFavourite(favourite: Favourite)
    suspend fun deleteFavourite(contentId: Int)
}