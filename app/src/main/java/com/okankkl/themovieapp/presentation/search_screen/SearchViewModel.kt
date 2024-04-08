package com.okankkl.themovieapp.presentation.search_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okankkl.themovieapp.common.Resources
import com.okankkl.themovieapp.domain.model.Content
import com.okankkl.themovieapp.domain.repository.ApiRepository
import com.okankkl.themovieapp.domain.use_cases.search_content.SearchContentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchContentUseCase: SearchContentUseCase)
    : ViewModel()
{
    private var _state = MutableStateFlow(SearchState())
    var state = _state.asStateFlow()

    fun search(query : String){
        searchContentUseCase.searchContent(query).onEach { result ->
            _state.value = when(result){
                is Resources.Success -> SearchState(data = result.data ?: emptyList())
                is Resources.Loading -> SearchState()
                is Resources.Failed -> SearchState(message = result.message)
            }
        }.launchIn(scope = CoroutineScope(Dispatchers.IO))
    }
}