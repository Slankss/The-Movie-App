package com.okankkl.themovieapp.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.okankkl.themovieapp.domain.model.Content
import com.okankkl.themovieapp.domain.model.Favourite

@Dao
interface ContentDao
{
    @Query("SELECT * FROM Display WHERE mediaType = :mediaType")
    fun getContents(mediaType: String): List<Content>
    @Upsert
    suspend fun addContents(movieList: List<Content>)

    @Query("DELETE FROM Display WHERE mediaType = :mediaType")
    fun deleteContents(mediaType: String)

    @Query("SELECT * FROM Favourites WHERE type = :displayType ORDER BY id DESC")
    fun getFavourites(displayType: String): List<Favourite>

    @Query("SELECT * FROM Favourites WHERE contentId = :contentId AND type = :displayType")
    fun getFavourite(contentId: Int, displayType: String): Favourite?

    @Insert
    fun addFavourite(favourite: Favourite)

    @Query("DELETE FROM Favourites WHERE contentId = :contentId AND type = :displayType")
    fun deleteFavourite(contentId: Int, displayType: String)
}