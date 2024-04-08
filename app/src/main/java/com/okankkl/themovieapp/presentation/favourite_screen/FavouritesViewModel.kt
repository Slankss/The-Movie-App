package com.okankkl.themovieapp.presentation.favourite_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.okankkl.themovieapp.common.Resources
import com.okankkl.themovieapp.presentation.DisplayType
import com.okankkl.themovieapp.domain.model.Favourite
import com.okankkl.themovieapp.domain.use_cases.delete_favourite_from_room.DeleteFavouriteFromRoomUseCase
import com.okankkl.themovieapp.domain.use_cases.get_favourites_from_room.GetFavouritesFromRoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@OptIn(DelicateCoroutinesApi::class)
@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val getFavouritesFromRoomUseCase: GetFavouritesFromRoomUseCase,
    private val deleteFavouriteFromRoomUseCase: DeleteFavouriteFromRoomUseCase
)
    : ViewModel()
{
    private var _favourites = MutableStateFlow(FavouriteState(is_loading = true))
    var favourites = _favourites.asStateFlow()

    fun getFavourites(displayType: DisplayType){
        getFavouritesFromRoomUseCase.getFavourites(displayType).onEach { result ->
            _favourites.value = when(result){
                is Resources.Success -> FavouriteState(data = result.data)
                is Resources.Loading -> FavouriteState(is_loading = true)
                is Resources.Failed -> FavouriteState(message = result.message)
            }
        }.launchIn(scope = CoroutineScope(Dispatchers.IO))
    }
    fun deleteFavourite(favourite : Favourite, displayType: DisplayType){
        deleteFavouriteFromRoomUseCase.deleteFavourite(favourite.contentId,displayType.path).onEach { result ->
            if(result is Resources.Success)
                getFavourites(displayType)
        }.launchIn(scope = CoroutineScope(Dispatchers.IO))
    }

}