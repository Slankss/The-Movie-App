package com.okankkl.themovieapp.domain.use_cases.delete_contents_from_room

import com.okankkl.themovieapp.domain.repository.RoomRepository
import com.okankkl.themovieapp.presentation.DisplayType
import javax.inject.Inject

class DeleteContentsFromRoomUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {
    suspend fun deleteContents(mediaType: DisplayType){
        try {
            roomRepository.deleteContents(mediaType.path)
        } catch (_: Exception){}
    }

}