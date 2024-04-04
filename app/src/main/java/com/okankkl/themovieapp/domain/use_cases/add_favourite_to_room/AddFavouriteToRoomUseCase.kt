package com.okankkl.themovieapp.domain.use_cases.add_favourite_to_room

import com.okankkl.themovieapp.common.Resources
import com.okankkl.themovieapp.domain.model.Favourite
import com.okankkl.themovieapp.domain.repository.RoomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddFavouriteToRoomUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {

    fun addFavourite(favourite: Favourite) : Flow<Resources<Boolean>> = flow {
        try {
            roomRepository.addFavourite(favourite)
            emit(Resources.Success(true))
        } catch (_ : Exception){
            emit(Resources.Failed(""))
        }
    }
}