package com.okankkl.themovieapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okankkl.themovieapp.enum_sealed.Categories
import com.okankkl.themovieapp.enum_sealed.DataType
import com.okankkl.themovieapp.enum_sealed.Pages
import com.okankkl.themovieapp.enum_sealed.Resources
import com.okankkl.themovieapp.model.StoreData
import com.okankkl.themovieapp.repository.RepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private var _selectedPage = MutableStateFlow(Pages.MovieList)
    var selectedPage = _selectedPage.asStateFlow()

    private var _popularMovies = MutableStateFlow<Resources>(Resources.Loading)
    var popularMovies = _popularMovies.asStateFlow()

    private var _trendMovies = MutableStateFlow<Resources>(Resources.Loading)
    var trendMovies = _trendMovies.asStateFlow()

    private var _topRatedMovies = MutableStateFlow<Resources>(Resources.Loading)
    var topRatedMovies = _topRatedMovies.asStateFlow()

    private var _nowPlayingMovies = MutableStateFlow<Resources>(Resources.Loading)
    var nowPlayingMovies = _nowPlayingMovies.asStateFlow()

    private var _popularTvSeries = MutableStateFlow<Resources>(Resources.Loading)
    var popularTvSeries = _popularTvSeries.asStateFlow()

    private var _trendTvSeries = MutableStateFlow<Resources>(Resources.Loading)
    var trendTvSeries = _trendTvSeries.asStateFlow()

    private var _topRatedTvSeries = MutableStateFlow<Resources>(Resources.Loading)
    var topRatedTvSeries = _topRatedTvSeries.asStateFlow()

    private var _onTheAirTvSeries = MutableStateFlow<Resources>(Resources.Loading)
    var onTheAirTvSeries = _onTheAirTvSeries.asStateFlow()


    var movieUpdateTime : String? = null
    var tvSeriesUpdateTime : String? = null

    fun setSelectedPage(page : Pages){
        _selectedPage.value = page
    }
    fun getMovies(){
        viewModelScope.launch {
            movieUpdateTime = storeData.getMovieUpdateTime.first()
            val currentDate = LocalDateTime.now()
            if(movieUpdateTime == null || movieUpdateTime!!.isEmpty()){
                refreshMovies()
                storeData.saveMovieUpdateTime(currentDate.toString())
            }
            else{
                val lastUpdateTime = LocalDateTime.parse(movieUpdateTime)
                val difference = lastUpdateTime.until(currentDate, ChronoUnit.MINUTES)
                if(difference >= 5){
                    refreshMovies()
                    storeData.saveMovieUpdateTime(currentDate.toString())
                }
            }
        }
    }

    fun refreshMovies(){
        viewModelScope.launch {
            _popularMovies.value =  repository.getDisplayList(DataType.Movie(),Categories.Popular,1)
            _trendMovies.value = repository.getDisplayList(DataType.Movie(),Categories.Trending,1)
            _topRatedMovies.value = repository.getDisplayList(DataType.Movie(),Categories.TopRated,1)
            _nowPlayingMovies.value = repository.getDisplayList(DataType.Movie(),Categories.NowPlaying,1)
        }
    }

    fun getTvSeries(){
        viewModelScope.launch {
            tvSeriesUpdateTime = storeData.getTvSeriesUpdateTime.first()
            val currentDate = LocalDateTime.now()
            if(tvSeriesUpdateTime == null || tvSeriesUpdateTime!!.isEmpty()){
                refreshTvSeries()
                storeData.saveTvSeriesUpdateTime(currentDate.toString())

            }
            else{
                val lastUpdateTime = LocalDateTime.parse(tvSeriesUpdateTime)
                val difference = lastUpdateTime.until(currentDate, ChronoUnit.MINUTES)
                if(difference >= 5){
                    refreshTvSeries()
                    storeData.saveTvSeriesUpdateTime(currentDate.toString())
                }

            }
        }
    }

    fun refreshTvSeries(){
        viewModelScope.launch {
            _popularTvSeries.value = repository.getDisplayList(DataType.TvSeries(),Categories.Popular,1)
            _trendTvSeries.value = repository.getDisplayList(DataType.TvSeries(),Categories.Trending,1)
            _topRatedTvSeries.value = repository.getDisplayList(DataType.TvSeries(),Categories.TopRated,1)
            _onTheAirTvSeries.value = repository.getDisplayList(DataType.TvSeries(),Categories.OnTheAir,1)
        }
    }


}