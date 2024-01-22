package com.okankkl.themovieapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okankkl.themovieapp.model.Search
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

    private var _searchList = MutableStateFlow(listOf<Search>())
    var searchList = _searchList.asStateFlow()

    fun search(query : String){

        viewModelScope.launch {
            _searchList.value = repository.search(query)
        }

    }


}