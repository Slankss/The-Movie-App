package com.okankkl.themovieapp.domain.use_cases.delete_favourite_from_room

import com.okankkl.themovieapp.common.Resources
import com.okankkl.themovieapp.domain.model.Favourite
import com.okankkl.themovieapp.domain.repository.RoomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteFavouriteFromRoomUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {
    fun deleteFavourite(contentId : Int,displayType: String) : Flow<Resources<Boolean>> = flow {
        try {
            roomRepository.deleteFavourite(contentId,displayType)
            emit(Resources.Success(true))
        } catch (_ : Exception){
            emit(Resources.Failed(""))
        }
    }

}