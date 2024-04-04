package com.okankkl.themovieapp.data.local.database.repository

import com.okankkl.themovieapp.data.local.database.ContentDao
import com.okankkl.themovieapp.domain.model.Content
import com.okankkl.themovieapp.domain.model.Favourite
import com.okankkl.themovieapp.domain.repository.RoomRepository
import com.okankkl.themovieapp.presentation.DisplayType
import javax.inject.Inject

class RoomRepositoryImp @Inject constructor(
    private val contentDao : ContentDao
) : RoomRepository {
    override suspend fun getContents(mediaType: String): List<Content> {
        return contentDao.getContents(mediaType)
    }

    override suspend fun addContents(contentList: List<Content>) {
        contentDao.addContents(contentList)
    }

    override suspend fun deleteContents(mediaType: String) {
        contentDao.deleteContents(mediaType)
    }

    override suspend fun getFavourites(displayType: DisplayType): List<Favourite> {
        return contentDao.getFavourites(displayType.path)
    }

    override suspend fun getFavourite(contentId: Int, displayType: String): Favourite? {
        return contentDao.getFavourite(contentId,displayType)
    }

    override suspend fun addFavourite(favourite: Favourite) {
        contentDao.addFavourite(favourite)
    }

    override suspend fun deleteFavourite(contentId: Int, displayType: String) {
        contentDao.deleteFavourite(contentId,displayType)
    }
}