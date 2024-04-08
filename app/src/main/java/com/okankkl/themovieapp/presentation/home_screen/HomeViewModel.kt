package com.okankkl.themovieapp.presentation.home_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okankkl.themovieapp.common.Resources
import com.okankkl.themovieapp.presentation.DisplayType
import com.okankkl.themovieapp.data.local.preferences.StoreData
import com.okankkl.themovieapp.domain.use_cases.add_contents_to_room.AddContentsToRoomUseCase
import com.okankkl.themovieapp.domain.use_cases.delete_contents_from_room.DeleteContentsFromRoomUseCase
import com.okankkl.themovieapp.domain.use_cases.get_movies.GetMoviesFromInternetUseCase
import com.okankkl.themovieapp.domain.use_cases.get_movies.GetMoviesFromRoomUseCase
import com.okankkl.themovieapp.domain.use_cases.get_tv_series.GetTvSeriesFromInternetUseCase
import com.okankkl.themovieapp.domain.use_cases.get_tv_series.GetTvSeriesFromRoomUseCase
import com.okankkl.themovieapp.presentation.Categories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject
@OptIn(DelicateCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    var storeData : StoreData,
    private var getMoviesFromInternetUseCase: GetMoviesFromInternetUseCase,
    private var getTvSeriesFromInternetUseCase: GetTvSeriesFromInternetUseCase,
    private var getMoviesFromRoomUseCase: GetMoviesFromRoomUseCase,
    private var getTvSeriesFromRoomUseCase: GetTvSeriesFromRoomUseCase,
    private var addContentsToRoomUseCase: AddContentsToRoomUseCase,
    private val deleteContentsFromRoomUseCase: DeleteContentsFromRoomUseCase
) : ViewModel()
{

    private var _selectedPage = MutableStateFlow(DisplayType.Movie)
    var selectedPage = _selectedPage.asStateFlow()

    private val _state = mutableStateOf(ContentState())
    val state : State<ContentState> = _state

    var movieUpdateTime : String? = null
    var tvSeriesUpdateTime : String? = null

    fun setSelectedPage(page : DisplayType){
        _selectedPage.value = page
        if(page == DisplayType.Movie)
            getMovies()
        else
            getTvSeries()
    }
    fun getMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            movieUpdateTime = storeData.getMovieUpdateTime.first()
            val currentDate = LocalDateTime.now()

            if(movieUpdateTime == null || movieUpdateTime!!.isEmpty()){
                getMoviesFromAPI()
                storeData.saveMovieUpdateTime(currentDate.toString())
            }
            else{
                val lastUpdateTime = LocalDateTime.parse(movieUpdateTime)
                val difference = lastUpdateTime.until(currentDate, ChronoUnit.MINUTES)
                if(difference >= 5){
                    getMoviesFromAPI()
                    storeData.saveMovieUpdateTime(currentDate.toString())
                } else {
                   getMoviesFromRoom()
                }
            }
        }
    }
    private fun getMoviesFromRoom(){
        getMoviesFromRoomUseCase().onEach { result ->
            _state.value = when(result){
                is Resources.Success -> ContentState(data = result.data)
                is Resources.Loading -> ContentState(is_loading = true)
                is Resources.Failed -> ContentState(message = result.message)
                }
        }.launchIn(scope = CoroutineScope(Dispatchers.IO))
    }
    private fun addMoviesToRoom()  {
        GlobalScope.launch(Dispatchers.IO) {
            launch {
                deleteContentsFromRoomUseCase.deleteContents(DisplayType.Movie)
            }.join()
            if(_state.value.data != null) addContentsToRoomUseCase.addContents(_state.value.data!!)
        }
    }
    private fun getMoviesFromAPI(){
        GlobalScope.launch {
            getMoviesFromInternetUseCase().onEach { result ->
                _state.value = when(result){
                    is Resources.Success -> {
                        ContentState(data = result.data)
                    }
                    is Resources.Failed -> ContentState(message = result.message)
                    is Resources.Loading -> ContentState(is_loading = true)
                }
            }.launchIn(scope = CoroutineScope(Dispatchers.IO)).join()

            addMoviesToRoom()
        }
    }
    fun getTvSeries(){
        viewModelScope.launch {
            tvSeriesUpdateTime = storeData.getTvSeriesUpdateTime.first()
            val currentDate = LocalDateTime.now()
            if(tvSeriesUpdateTime == null || tvSeriesUpdateTime!!.isEmpty()){
                getTvSeriesFromAPI()
                storeData.saveTvSeriesUpdateTime(currentDate.toString())
            }
            else{
                val lastUpdateTime = LocalDateTime.parse(tvSeriesUpdateTime)
                val difference = lastUpdateTime.until(currentDate, ChronoUnit.MINUTES)
                if(difference >= 5){
                    getTvSeriesFromAPI()
                    storeData.saveTvSeriesUpdateTime(currentDate.toString())
                } else {
                    getTvSeriesFromRoom()
                }
            }
        }
    }
    private fun getTvSeriesFromRoom(){
        getTvSeriesFromRoomUseCase.getTvSeries().onEach { result ->
            _state.value = when(result){
                is Resources.Success -> ContentState(data = result.data)
                is Resources.Loading -> ContentState(is_loading = true)
                is Resources.Failed -> ContentState(message = result.message)
            }
        }.launchIn(scope = CoroutineScope(Dispatchers.IO))
    }
    private fun addTvSeriesToRoom(){
        GlobalScope.launch(Dispatchers.IO) {
            launch {
                deleteContentsFromRoomUseCase.deleteContents(DisplayType.TvSeries)
            }.join()
            if(_state.value.data != null) addContentsToRoomUseCase.addContents(_state.value.data!!)
        }
    }
    private fun getTvSeriesFromAPI(){
        GlobalScope.launch(Dispatchers.IO) {
            getTvSeriesFromInternetUseCase.getTvSeries().onEach { result ->
                _state.value = when(result){
                    is Resources.Success -> ContentState(data = result.data)
                    is Resources.Failed -> ContentState(message = result.message)
                    is Resources.Loading -> ContentState(is_loading = true)
                }
            }.launchIn(scope = CoroutineScope(Dispatchers.IO)).join()
            addTvSeriesToRoom()
        }
    }
}