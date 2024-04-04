package com.okankkl.themovieapp.domain.use_cases.get_favourites_from_room

import com.okankkl.themovieapp.common.Resources
import com.okankkl.themovieapp.domain.model.Favourite
import com.okankkl.themovieapp.domain.repository.RoomRepository
import com.okankkl.themovieapp.presentation.DisplayType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavouritesFromRoomUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {

    fun getFavourites(displayType: DisplayType) : Flow<Resources<List<Favourite>>> = flow {
        try {
            emit(Resources.Loading())
            val favourites = roomRepository.getFavourites(displayType)
            if(favourites.isNotEmpty())
                emit(Resources.Success(data = favourites))
            else
                emit(Resources.Failed(message = "Favourites are empty."))
        } catch (e : Exception){
            emit(Resources.Failed(message = e.localizedMessage ?: "Unexpected error occured."))
        }
    }
}