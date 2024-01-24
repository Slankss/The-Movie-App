package com.okankkl.themovieapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okankkl.themovieapp.model.Display
import com.okankkl.themovieapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
    @Inject
    constructor(private val repository: Repository)
    : ViewModel()
{

    private var _searchList = MutableStateFlow<List<Display>?>(null)
    var searchList = _searchList.asStateFlow()

    private var _loadingState = MutableStateFlow(false)
    var loadingState = _loadingState.asStateFlow()

    fun search(query : String){
        viewModelScope.launch {
            _searchList.value = null
            _searchList.value = repository.search(query)
        }
    }

    fun clearList(){
        viewModelScope.launch {
            _searchList.value = null
        }
    }

    fun setLoadingState(state : Boolean){
        viewModelScope.launch{
            _loadingState.value = state
        }
    }


}