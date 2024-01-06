package com.okankkl.themovieapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okankkl.themovieapp.enum_sealed.DataType
import com.okankkl.themovieapp.enum_sealed.Resources
import com.okankkl.themovieapp.repository.RepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisplayDetailViewModel
    @Inject
    constructor(var repository: RepositoryImp
) : ViewModel()
{
    private val _display = MutableStateFlow<Resources>(Resources.Loading)
    var display = _display.asStateFlow()

    private val _similarDisplayList = MutableStateFlow<Resources>(Resources.Loading)
    var similarDisplayList = _similarDisplayList.asStateFlow()

    fun getDisplay(type : DataType,id : Int){
        viewModelScope.launch {
            _display.value = repository.getDisplayDetail(type,id)
            _similarDisplayList.value = repository.getSimilarDisplayList(type,id)
        }
    }

}