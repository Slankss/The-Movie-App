package com.okankkl.themovieapp.domain.repository

import com.okankkl.themovieapp.utils.Resources
import com.okankkl.themovieapp.data.model.entity.Favourite

interface FavouritesRepository {

    suspend fun getFavourites() : Resources<List<Favourite>>

    suspend fun getFavourite(id: Int, contentType: String): Resources<Favourite?>

    suspend fun addFavourite(favourite: Favourite) : Resources<Long>

    suspend fun deleteFavourite(contentId: Int, contentType: String) : Resources<Unit>
}