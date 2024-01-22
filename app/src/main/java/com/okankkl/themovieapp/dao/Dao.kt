package com.okankkl.themovieapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.okankkl.themovieapp.model.Display
import com.okankkl.themovieapp.model.Favourite
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.model.MovieEntity
import com.okankkl.themovieapp.model.TvSeries

@Dao
interface Dao
{
    @Query("SELECT * FROM Display WHERE mediaType = :mediaType")
    fun getDisplays(mediaType : String) : List<Display>

    @Upsert
    suspend fun addDisplays(movieList : List<Display>)

    @Query("DELETE FROM Display WHERE mediaType = :mediaType")
    fun deleteDisplays(mediaType: String)

    @Query("SELECT * FROM Favourites WHERE type = :displayType ORDER BY id DESC")
    fun getFavourites(displayType : String) : List<Favourite>

    @Query("SELECT * FROM Favourites WHERE contentId = :contentId")
    fun getFavourite(contentId: Int) : Favourite?

    @Insert
    fun addFavourite(favourite: Favourite)

    @Query("DELETE FROM Favourites WHERE contentId = :contentId")
    fun deleteFavourite(contentId : Int)
}