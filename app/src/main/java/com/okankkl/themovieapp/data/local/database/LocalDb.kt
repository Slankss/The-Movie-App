package com.okankkl.themovieapp.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.okankkl.themovieapp.data.model.entity.Favourite
import com.okankkl.themovieapp.data.model.entity.MovieEntity
import com.okankkl.themovieapp.data.model.entity.TvSeriesEntity

@Dao
interface LocalDb
{
    @Query("SELECT * FROM Movie")
    fun getMovies(): List<MovieEntity>

    @Insert
    suspend fun addMovies(movieDtoList: List<MovieEntity>)

    @Query("DELETE FROM Movie")
    fun deleteMovies()

    @Query("SELECT * FROM TvSeries")
    fun getTvSeries(): List<TvSeriesEntity>

    @Insert
    suspend fun addTvSeries(tvSeriesList: List<TvSeriesEntity>)

    @Query("DELETE FROM TvSeries")
    fun deleteTvSeries()

    @Query("SELECT * FROM Favourites ORDER BY id DESC")
    fun getFavourites(): List<Favourite>

    @Query("SELECT * FROM Favourites WHERE contentId = :contentId AND contentType = :contentType")
    fun getFavourite(contentId: Int, contentType: String): Favourite?

    @Insert
    fun addFavourite(favourite: Favourite) : Long

    @Query("DELETE FROM Favourites WHERE contentId = :contentId AND contentType = :contentType")
    fun deleteFavourite(contentId: Int, contentType: String)
}