package com.okankkl.themovieapp.repository

import androidx.paging.PagingData
import com.okankkl.themovieapp.enum_sealed.Categories
import com.okankkl.themovieapp.enum_sealed.DisplayType
import com.okankkl.themovieapp.enum_sealed.Resources
import com.okankkl.themovieapp.model.Display
import com.okankkl.themovieapp.model.Favourite
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.model.TvSeries
import kotlinx.coroutines.flow.Flow


interface Repository
{
    suspend fun getMoviesFromAPI(moviesType: Categories, page : Int) : List<Movie>
    suspend fun getMovieDetail(id : Int) : Resources
    suspend fun getSimilarMovies(id : Int) : List<Movie>

    
    suspend fun getMoviesPage(category : Categories) : Flow<PagingData<Movie>>
    suspend fun getTvSeriesListFromAPI(category: Categories, page : Int): List<TvSeries>
    suspend fun getTvSeriesDetail(id : Int) : Resources
    suspend fun getSimilarTvSeries(id : Int) : List<TvSeries>

    suspend fun getTvSeriesPage(category: Categories): Flow<PagingData<TvSeries>>

    suspend fun getDisplaysFromRoom(mediaType : String) : List<Display>
    suspend fun addDisplaysToRoom(displayList : List<Display>)
    suspend fun deleteDisplaysFromRoom(mediaType : String)

    suspend fun getFavourites(displayType: DisplayType) : List<Favourite>
    suspend fun getFavourite(contentId : Int,displayType: String) : Favourite?
    suspend fun addFavourite(favourite: Favourite)
    suspend fun deleteFavourite(contentId: Int,displayType: String)
    suspend fun search(query : String) : List<Display>
}