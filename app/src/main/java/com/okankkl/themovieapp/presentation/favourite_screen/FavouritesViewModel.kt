package com.okankkl.themovieapp.presentation.favourite_screen

import androidx.lifecycle.ViewModel
import com.okankkl.themovieapp.common.Resources
import com.okankkl.themovieapp.presentation.DisplayType
import com.okankkl.themovieapp.domain.model.Favourite
import com.okankkl.themovieapp.domain.repository.ApiRepository
import com.okankkl.themovieapp.domain.use_cases.delete_favourite_from_room.DeleteFavouriteFromRoomUseCase
import com.okankkl.themovieapp.domain.use_cases.get_favourites_from_room.GetFavouritesFromRoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val getFavouritesFromRoomUseCase: GetFavouritesFromRoomUseCase,
    private val deleteFavouriteFromRoomUseCase: DeleteFavouriteFromRoomUseCase
)
    : ViewModel()
{

    private var _favourites = MutableStateFlow(FavouriteState())
    var favourites = _favourites.asStateFlow()

    fun getFavourites(displayType: DisplayType){
        getFavouritesFromRoomUseCase.getFavourites(displayType).onEach { result ->
            _favourites.value = when(result){
                is Resources.Success -> FavouriteState(data = result.data)
                is Resources.Loading -> FavouriteState()
                is Resources.Failed -> FavouriteState(message = result.message)
            }
        }
    }
    fun deleleteFavourite(favourite : Favourite, displayType: DisplayType){
        deleteFavouriteFromRoomUseCase.deleteFavourite(favourite.contentId,displayType.path).onEach { result ->
            if(result.data != null && result.data == true)
                getFavourites(displayType)
        }
    }

}