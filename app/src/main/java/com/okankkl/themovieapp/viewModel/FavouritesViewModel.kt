package com.okankkl.themovieapp.viewModel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okankkl.themovieapp.enum_sealed.DisplayType
import com.okankkl.themovieapp.enum_sealed.Resources
import com.okankkl.themovieapp.model.Favourite
import com.okankkl.themovieapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel
    @Inject
    constructor(private val repository : Repository)
    : ViewModel()
{

    private var _favourites = MutableStateFlow<List<Favourite>?>(null)
    var favourites = _favourites.asStateFlow()

    @OptIn(DelicateCoroutinesApi::class)
    fun getFavourites(displayType: DisplayType){
        GlobalScope.launch(Dispatchers.IO) {
            _favourites.value = null
            _favourites.value = repository.getFavourites(displayType)
        }
    }

}