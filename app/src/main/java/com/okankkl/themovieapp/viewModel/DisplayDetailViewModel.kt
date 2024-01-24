package com.okankkl.themovieapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okankkl.themovieapp.enum_sealed.DisplayType
import com.okankkl.themovieapp.enum_sealed.Resources
import com.okankkl.themovieapp.model.Display
import com.okankkl.themovieapp.model.Favourite
import com.okankkl.themovieapp.model.Movie
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
class DisplayDetailViewModel
    @Inject
    constructor(var repository: RepositoryImp
) : ViewModel()
{
    private val _display = MutableStateFlow<Resources>(Resources.Loading)
    var display = _display.asStateFlow()

    private val _similarDisplays = MutableStateFlow(listOf<Display>())
    var similarDisplays = _similarDisplays.asStateFlow()

    private val _favouriteState = MutableStateFlow<Favourite?>(null)
    val favouriteState = _favouriteState.asStateFlow()

    fun getDisplay(id : Int,displayType : String){
        viewModelScope.launch {
            when(displayType){
                DisplayType.Movie.path -> {
                    _display.value = repository.getMovieDetail(id)
                    _similarDisplays.value = repository.getSimilarMovies(id)
                }
                else -> {
                    _display.value = repository.getTvSeriesDetail(id)
                    _similarDisplays.value = repository.getSimilarTvSeries(id)
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getFavourite(contentId : Int,displayType: String){
        GlobalScope.launch(Dispatchers.IO){
            _favouriteState.value = repository.getFavourite(contentId,displayType)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun addFavourite(display : Display,displayType: String){
        GlobalScope.launch(Dispatchers.IO) {
            val addJob = launch {
                val time = when(display){
                    is Movie -> "${display.runtime} minutes"
                    is TvSeries ->  "${display.numberOfSeasons } seasons"
                    else -> ""
                }

                val favourite = Favourite(displayType,display.en_title,display.backdropPath ?: "",display.posterPath ?: "",
                    display.id,display.releaseDate,display.voteAverage,time)
                repository.addFavourite(favourite)
            }
            addJob.invokeOnCompletion {
                getFavourite(display.id,displayType)
            }

        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun deleteFavourite(display: Display, displayType: String){
        GlobalScope.launch(Dispatchers.IO){
            val deleteJob = launch {
                repository.deleteFavourite(display.id,displayType)
            }
            deleteJob.invokeOnCompletion {
                _favouriteState.value = null
            }
        }
    }

}