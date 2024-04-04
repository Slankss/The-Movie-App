package com.okankkl.themovieapp.domain.repository

import com.okankkl.themovieapp.domain.model.Content
import com.okankkl.themovieapp.domain.model.Favourite
import com.okankkl.themovieapp.presentation.DisplayType

interface RoomRepository {
    suspend fun getContents(mediaType : String) : List<Content>
    suspend fun addContents(contentList : List<Content>)
    suspend fun deleteContents(mediaType : String)

    suspend fun getFavourites(displayType: DisplayType) : List<Favourite>
    suspend fun getFavourite(contentId : Int,displayType: String) : Favourite?
    suspend fun addFavourite(favourite: Favourite)
    suspend fun deleteFavourite(contentId: Int,displayType: String)
}