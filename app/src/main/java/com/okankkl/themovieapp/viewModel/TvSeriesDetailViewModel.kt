package com.okankkl.themovieapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okankkl.themovieapp.enum_sealed.Resources
import com.okankkl.themovieapp.repository.RepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvSeriesDetailViewModel
    @Inject
    constructor(var repository: RepositoryImp)
    : ViewModel()
{
      private var _tvSeries = MutableStateFlow<Resources>(Resources.Loading)
      var tvSeries = _tvSeries.asStateFlow()

      private var _similarTvSeries = MutableStateFlow<Resources>(Resources.Loading)
      var similarTvSeries = _similarTvSeries.asStateFlow()

     fun getTvSeries(id : Int){

         viewModelScope.launch {
             _tvSeries.value = repository.getTvSeriesDetail(id)
             _similarTvSeries.value = repository.getSimilarTvSeries(id)
         }

     }


}