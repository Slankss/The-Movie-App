package com.okankkl.themovieapp.data.repository

import com.okankkl.themovieapp.utils.Resources
import com.okankkl.themovieapp.data.datasource.favourites.FavouritesDataSource
import com.okankkl.themovieapp.data.model.entity.Favourite
import com.okankkl.themovieapp.domain.repository.FavouritesRepository
import javax.inject.Inject

class FavouritesRepositoryImpl @Inject constructor(
    private var dataSource: FavouritesDataSource
) : FavouritesRepository {
    override suspend fun getFavourites(): Resources<List<Favourite>> {
        return dataSource.getFavourites()
    }

    override suspend fun getFavourite(id: Int, contentType: String): Resources<Favourite?> {
        return dataSource.getFavourite(id,contentType)
    }

    override suspend fun addFavourite(favourite: Favourite) : Resources<Long> {
        return dataSource.addFavourite(favourite)
    }

    override suspend fun deleteFavourite(contentId: Int, contentType: String) : Resources<Unit> {
        return dataSource.deleteFavourite(contentId, contentType)
    }
}