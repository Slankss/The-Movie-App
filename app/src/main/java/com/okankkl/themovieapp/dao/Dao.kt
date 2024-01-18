package com.okankkl.themovieapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.okankkl.themovieapp.model.Favourite
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.model.MovieEntity
import com.okankkl.themovieapp.model.TvSeries

@Dao
interface Dao
{
    @Query("SELECT * FROM Movie WHERE category LIKE '%' || :category || '%' ORDER BY :order_by DESC")
    fun getMovies(category : String,order_by : String) : List<Movie>

    @Upsert
    suspend fun addMovies(movieList : List<Movie>)

    @Query("DELETE FROM Movie")
    fun deleteMovies()

    @Query("SELECT * FROM TvSeries WHERE category LIKE '%' || :category || '%'")
    fun getTvSeries(category : String) : List<TvSeries>

    @Upsert
    suspend fun addTvSeries(movieList : List<TvSeries>)

    @Query("DELETE FROM TvSeries")
    fun deleteTvSeries()

    @Query("SELECT * FROM Favourites WHERE type = :displayType ORDER BY id DESC")
    fun getFavourites(displayType : String) : List<Favourite>

    @Query("SELECT * FROM Favourites WHERE contentId = :contentId")
    fun getFavourite(contentId: Int) : Favourite?

    @Insert
    fun addFavourite(favourite: Favourite)

    @Query("DELETE FROM Favourites WHERE contentId = :contentId")
    fun deleteFavourite(contentId : Int)
}