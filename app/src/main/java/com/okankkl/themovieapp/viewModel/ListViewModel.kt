package com.okankkl.themovieapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okankkl.themovieapp.enum_sealed.Categories
import com.okankkl.themovieapp.enum_sealed.DisplayType
import com.okankkl.themovieapp.model.Display
import com.okankkl.themovieapp.model.StoreData
import com.okankkl.themovieapp.repository.RepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class ListViewModel
    @Inject
    constructor(var repository: RepositoryImp, var storeData : StoreData) : ViewModel()
{
    private var _selectedPage = MutableStateFlow(DisplayType.Movie)
    var selectedPage = _selectedPage.asStateFlow()

    private var _loadingState = MutableStateFlow(true)
    var loadingState = _loadingState.asStateFlow()

    private val _allDisplayList = MutableStateFlow(listOf<Display>())
    val allDisplayList = _allDisplayList.asStateFlow()

    var movieUpdateTime : String? = null
    var tvSeriesUpdateTime : String? = null

    fun setSelectedPage(page : DisplayType){
        _selectedPage.value = page
        setLoadingState(true)
        if(page == DisplayType.Movie)
            getMovies()
        else
            getTvSeries()
    }

    fun setLoadingState(state : Boolean){
        _loadingState.value = state
    }

    fun getMovies(){
        viewModelScope.launch {
            setLoadingState(true)
            movieUpdateTime = storeData.getMovieUpdateTime.first()
            val currentDate = LocalDateTime.now()
            _allDisplayList.value = emptyList()
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

    @OptIn(DelicateCoroutinesApi::class)
    fun getMoviesFromRoom(){
        GlobalScope.launch(Dispatchers.IO) {
            _allDisplayList.value = repository.getDisplaysFromRoom(DisplayType.Movie.path)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun addMoviesToRoom(filteredAllMovieList : List<Display>)  {
        GlobalScope.launch(Dispatchers.IO) {
            val deleteJob = launch {
                repository.deleteDisplaysFromRoom(DisplayType.Movie.path)
            }
            deleteJob.join()

            val addJob = launch {
                repository.addDisplaysToRoom(filteredAllMovieList.toList())
            }
            addJob.join()
        }

    }

    @OptIn(DelicateCoroutinesApi::class)
    private suspend fun getMoviesFromAPI(){
        // 1.54
        GlobalScope.launch(Dispatchers.IO) {
            _allDisplayList.value = emptyList()
            val apiJob = launch {
                _allDisplayList.value = repository.getMoviesFromAPI(Categories.Popular,1) +
                        repository.getMoviesFromAPI(Categories.Trending,1) +
                        repository.getMoviesFromAPI(Categories.TopRated,1) +
                        repository.getMoviesFromAPI(Categories.NowPlaying,1)
            }
            apiJob.join()
            apiJob.invokeOnCompletion {
                if(it == null && _allDisplayList.value.isNotEmpty()) setLoadingState(false)
                else setLoadingState(true)
            }
            val roomJob = launch {
                val filteredAllMovieList = getAllMovieList()
                addMoviesToRoom(filteredAllMovieList)
            }
            roomJob.join()

        }

    }

    fun getTvSeries(){
        viewModelScope.launch {
            setLoadingState(true)
            tvSeriesUpdateTime = storeData.getTvSeriesUpdateTime.first()
            val currentDate = LocalDateTime.now()
            _allDisplayList.value = emptyList()
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

    @OptIn(DelicateCoroutinesApi::class)
    fun getTvSeriesFromRoom(){
        GlobalScope.launch(Dispatchers.IO) {
            _allDisplayList.value = repository.getDisplaysFromRoom(DisplayType.TvSeries.path)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun addTvSeriesToRoom(){
        GlobalScope.launch(Dispatchers.IO) {
            val deleteJob = launch {
                repository.deleteDisplaysFromRoom(DisplayType.TvSeries.path)
            }
            deleteJob.join()

            val addJob = launch {
                val filteredTvSeriesList = getAllTvSeriesList()
                repository.addDisplaysToRoom(filteredTvSeriesList.toList())
            }
            addJob.join()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getTvSeriesFromAPI(){
        GlobalScope.launch(Dispatchers.IO) {
            val apiJob = launch {
                _allDisplayList.value = (
                        repository.getTvSeriesListFromAPI(Categories.Popular,1) +
                        repository.getTvSeriesListFromAPI(Categories.Trending,1) +
                        repository.getTvSeriesListFromAPI(Categories.TopRated,1) +
                        repository.getTvSeriesListFromAPI(Categories.OnTheAir,1)
                        )
            }
            apiJob.join()
            val roomJob = launch {
                addTvSeriesToRoom()
            }
            roomJob.join()
        }
    }

    private fun getAllMovieList() : List<Display> {
        val list = mutableListOf<Display>()

        _allDisplayList.value.forEach { display ->
            display.mediaType = "movie"
            list.find { it.id == display.id }?.let {
                it.category += ("," + display.category)
            } ?: list.add(display)
        }

        return list.toList()
    }

    private fun getAllTvSeriesList() : List<Display> {
        val list = mutableListOf<Display>()

        _allDisplayList.value.forEach { display ->
            display.mediaType ="tv"
            list.find { it.id == display.id }?.let {
                it.category += ("," + display.category)
            } ?: list.add(display)
        }

        return list.toList()
    }


}