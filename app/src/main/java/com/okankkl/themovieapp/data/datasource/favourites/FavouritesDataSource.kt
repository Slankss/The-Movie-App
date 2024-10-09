package com.okankkl.themovieapp.data.datasource.favourites

import com.okankkl.themovieapp.utils.Resources
import com.okankkl.themovieapp.data.model.entity.Favourite
import com.okankkl.themovieapp.utils.ContentType

interface FavouritesDataSource {

    suspend fun getFavourites(): Resources<List<Favourite>>

    suspend fun getFavourite(contentId: Int, displayType: String): Resources<Favourite?>

    suspend fun addFavourite(favourite: Favourite) : Resources<Long>

    suspend fun deleteFavourite(contentId: Int, contentType: String) : Resources<Unit>
}