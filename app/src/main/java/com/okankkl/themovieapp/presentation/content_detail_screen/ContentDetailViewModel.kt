package com.okankkl.themovieapp.presentation.content_detail_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okankkl.themovieapp.common.Resources
import com.okankkl.themovieapp.data.local.database.repository.RoomRepositoryImp
import com.okankkl.themovieapp.domain.model.Content
import com.okankkl.themovieapp.domain.model.Favourite
import com.okankkl.themovieapp.domain.model.Movie
import com.okankkl.themovieapp.domain.model.TvSeries
import com.okankkl.themovieapp.data.remote.repository.ApiRepositoryImp
import com.okankkl.themovieapp.domain.repository.RoomRepository
import com.okankkl.themovieapp.domain.use_cases.add_favourite_to_room.AddFavouriteToRoomUseCase
import com.okankkl.themovieapp.domain.use_cases.delete_favourite_from_room.DeleteFavouriteFromRoomUseCase
import com.okankkl.themovieapp.domain.use_cases.get_favourite_from_room.GetFavouriteFromRoomUseCase
import com.okankkl.themovieapp.domain.use_cases.get_movie_detail.GetMovieDetailUseCase
import com.okankkl.themovieapp.domain.use_cases.get_similar_movies.GetSimilarMoviesUseCase
import com.okankkl.themovieapp.domain.use_cases.get_similar_tv_series.GetSimilarTvSeriesUseCase
import com.okankkl.themovieapp.domain.use_cases.get_tv_series_detail.GetTvSeriesDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentDetailViewModel @Inject constructor(
    val getMovieDetailUseCase: GetMovieDetailUseCase,
    val getTvSeriesDetailUseCase: GetTvSeriesDetailUseCase,
    val getFavouriteFromRoomUseCase: GetFavouriteFromRoomUseCase,
    val addFavouriteToRoomUseCase: AddFavouriteToRoomUseCase,
    var deleteFavouriteFromRoomUseCase: DeleteFavouriteFromRoomUseCase,
    val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    val getSimilarTvSeriesUseCase: GetSimilarTvSeriesUseCase,
) : ViewModel()
{
    private val _contentState = MutableStateFlow(ContentDetailState())
    var contentState = _contentState.asStateFlow()

    private val _similarContents = MutableStateFlow<List<Content>>(emptyList())
    var similarContents = _similarContents.asStateFlow()

    private val _favouriteState = MutableStateFlow(false)
    val favouriteState = _favouriteState.asStateFlow()

    fun getMovieDetail(id : Int){
        getMovieDetailUseCase.getMovieDetail(id).onEach { result ->
            _contentState.value = when(result){
                is Resources.Success -> ContentDetailState(data = result.data)
                is Resources.Loading -> ContentDetailState()
                is Resources.Failed -> ContentDetailState(message = result.message)
            }
        }.launchIn(viewModelScope)
    }
    fun getTvSeriesDetail(id : Int){
        getTvSeriesDetailUseCase.getTvSeriesDetail(id).onEach { result ->
            _contentState.value = when(result){
                is Resources.Success -> ContentDetailState(data = result.data)
                is Resources.Loading -> ContentDetailState()
                is Resources.Failed -> ContentDetailState(message = result.message)
            }
        }.launchIn(viewModelScope)
    }

    fun getSimilarMovies(id : Int){
        getSimilarMoviesUseCase.getSimilarMovies(id).onEach { result ->
            _similarContents.value = when(result){
                is Resources.Success -> result.data ?: emptyList()
                else -> emptyList()
            }
        }.launchIn(viewModelScope)
    }

    fun getSimilarTvSeries(id : Int){
        getSimilarTvSeriesUseCase.getSimilarTvSeries(id).onEach { result ->
            _similarContents.value = when(result){
                is Resources.Success -> result.data ?: emptyList()
                else -> emptyList()
            }
        }.launchIn(viewModelScope)
    }
    fun getFavourite(contentId : Int,displayType: String){
        getFavouriteFromRoomUseCase.getFavourite(contentId,displayType).onEach { result ->
            _favouriteState.value = (result.data != null || result.data == true)
        }.launchIn(viewModelScope)
    }

    fun addFavourite(content : Content, displayType: String){
        val time = when(content){
            is Movie -> "${content.runtime} minutes"
            is TvSeries ->  "${content.numberOfSeasons } seasons"
            else -> ""
        }

        val favourite = Favourite(displayType,content.title,content.backdropPath ?: "",content.posterPath ?: "",
            content.id,content.releaseDate,content.voteAverage,time)
        addFavouriteToRoomUseCase.addFavourite(favourite).onEach {  result ->
            _favouriteState.value = !(result.data == null || result.data == false)
        }.launchIn(viewModelScope)
    }

    fun deleteFavourite(content: Content, displayType: String){
        deleteFavouriteFromRoomUseCase.deleteFavourite(content.id,displayType).onEach { result ->
            _favouriteState.value = (result.data != null && result.data == true)
        }.launchIn(viewModelScope)
    }

}