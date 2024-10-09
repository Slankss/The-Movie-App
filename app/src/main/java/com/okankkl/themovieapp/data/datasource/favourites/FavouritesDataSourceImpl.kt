package com.okankkl.themovieapp.data.datasource.favourites

import com.okankkl.themovieapp.utils.Resources
import com.okankkl.themovieapp.utils.roomCall
import com.okankkl.themovieapp.data.local.database.LocalDb
import com.okankkl.themovieapp.data.model.entity.Favourite
import javax.inject.Inject

class FavouritesDataSourceImpl @Inject constructor(
    private val db: LocalDb
) : FavouritesDataSource {
    override suspend fun getFavourites(): Resources<List<Favourite>> {
        return roomCall { db.getFavourites() }
    }

    override suspend fun getFavourite(contentId: Int, displayType: String): Resources<Favourite?> {
        return roomCall { db.getFavourite(contentId, displayType) }
    }

    override suspend fun addFavourite(favourite: Favourite) : Resources<Long> {
        return roomCall { db.addFavourite(favourite) }
    }

    override suspend fun deleteFavourite(contentId: Int, contentType: String) : Resources<Unit> {
        return roomCall { db.deleteFavourite(contentId, contentType) }
    }
}