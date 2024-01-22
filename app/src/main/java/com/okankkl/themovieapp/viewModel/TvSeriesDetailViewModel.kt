package com.okankkl.themovieapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okankkl.themovieapp.enum_sealed.DisplayType
import com.okankkl.themovieapp.enum_sealed.Resources
import com.okankkl.themovieapp.model.Favourite
import com.okankkl.themovieapp.model.TvSeries
import com.okankkl.themovieapp.repository.RepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
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

    private val _favouriteState = MutableStateFlow<Favourite?>(null)
    val favouriteState = _favouriteState.asStateFlow()

     fun getTvSeries(id : Int){

         viewModelScope.launch {
             _tvSeries.value = repository.getTvSeriesDetail(id)
             _similarTvSeries.value = repository.getSimilarTvSeries(id)
         }

     }

    @OptIn(DelicateCoroutinesApi::class)
    fun getFavourite(contentId : Int){
        GlobalScope.launch(Dispatchers.IO){
            _favouriteState.value = repository.getFavourite(contentId)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun addFavourite(tvSeries : TvSeries){
        GlobalScope.launch(Dispatchers.IO) {
            val addJob = launch {
                val time = "${tvSeries.numberOfSeasons } seasons"
                val favourite = Favourite(DisplayType.TvSeries.path,tvSeries.title,tvSeries.backdropPath ?: "",tvSeries.posterPath ?: "",
                    tvSeries.id,tvSeries.releaseDate,tvSeries.voteAverage,time)
                repository.addFavourite(favourite)
            }
            addJob.invokeOnCompletion {
                getFavourite(tvSeries.id)
            }

        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun deleteFavourite(tvSeries : TvSeries){
        GlobalScope.launch(Dispatchers.IO){
            val deleteJob = launch {
                repository.deleteFavourite(tvSeries.id)
            }
            deleteJob.invokeOnCompletion {
                _favouriteState.value = null
            }
        }
    }


}