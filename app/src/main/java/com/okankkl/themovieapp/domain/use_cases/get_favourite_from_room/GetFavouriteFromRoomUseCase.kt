package com.okankkl.themovieapp.domain.use_cases.get_favourite_from_room

import com.okankkl.themovieapp.common.Resources
import com.okankkl.themovieapp.domain.model.Favourite
import com.okankkl.themovieapp.domain.repository.RoomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavouriteFromRoomUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {
    fun getFavourite(contentId : Int,displayType: String) : Flow<Resources<Boolean>> = flow {
        try {
            val favourite = roomRepository.getFavourite(contentId,displayType)
            if(favourite == null)
                emit(Resources.Failed(""))
            else
                emit(Resources.Success(data = true))
        } catch (_ : Exception){
            emit(Resources.Failed(""))
        }
    }
}