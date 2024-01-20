package com.okankkl.themovieapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okankkl.themovieapp.enum_sealed.Categories
import com.okankkl.themovieapp.enum_sealed.Pages
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.model.StoreData
import com.okankkl.themovieapp.model.TvSeries
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
    private var _selectedPage = MutableStateFlow(Pages.MovieList)
    var selectedPage = _selectedPage.asStateFlow()

    private var _loadingState = MutableStateFlow(true)
    var loadingState = _loadingState.asStateFlow()

    private val _allMovieList = MutableStateFlow(listOf<Movie>())
    var allMovieList = _allMovieList.asStateFlow()

    private val _allTvSeriesList = MutableStateFlow(listOf<TvSeries>())
    var allTvSeriesList = _allTvSeriesList.asStateFlow()

    var movieUpdateTime : String? = null
    var tvSeriesUpdateTime : String? = null

    fun setSelectedPage(page : Pages){
        _selectedPage.value = page
        setLoadingState(true)
    }

    fun setLoadingState(state : Boolean){
        _loadingState.value = state
    }


    fun getMovies(){
        viewModelScope.launch {

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

    @OptIn(DelicateCoroutinesApi::class)
    fun getMoviesFromRoom(){
        GlobalScope.launch(Dispatchers.IO) {
            _allMovieList.value = repository.getMovieListFromRoom()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun addMoviesToRoom(filteredAllMovieList : List<Movie>)  {
        GlobalScope.launch(Dispatchers.IO) {
            val deleteJob = launch {
                repository.deleteMovieListFromRoom()
            }
            deleteJob.join()

            val addJob = launch {
                repository.addMovieListToRoom(filteredAllMovieList.toList())
            }
            addJob.join()
        }

    }

    @OptIn(DelicateCoroutinesApi::class)
    private suspend fun getMoviesFromAPI(){
        GlobalScope.launch(Dispatchers.IO) {
            val apiJob = launch {
                _allMovieList.value = repository.getMovieListFromAPI(Categories.Popular,1) +
                        repository.getMovieListFromAPI(Categories.Trending,1)  +
                        repository.getMovieListFromAPI(Categories.TopRated,1)  +
                        repository.getMovieListFromAPI(Categories.NowPlaying,1)

            }
            apiJob.join()
            apiJob.invokeOnCompletion {
                if(it == null && _allMovieList.value.isNotEmpty()) setLoadingState(false)
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

    @OptIn(DelicateCoroutinesApi::class)
    fun getTvSeriesFromRoom(){
        GlobalScope.launch(Dispatchers.IO) {
            _allTvSeriesList.value = repository.getTvSeriesListFromRoom() +
                    repository.getTvSeriesListFromRoom() +
                    repository.getTvSeriesListFromRoom() +
                    repository.getTvSeriesListFromRoom()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun addTvSeriesToRoom(){
        GlobalScope.launch(Dispatchers.IO) {
            val deleteJob = launch {
                repository.deleteTvSeriesListFromRoom()
            }
            deleteJob.join()

            val addJob = launch {
                val filteredTvSeriesList = getAllTvSeriesList()
                repository.addTvSeriesListToRoom(filteredTvSeriesList.toList())
            }
            addJob.join()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getTvSeriesFromAPI(){
        GlobalScope.launch(Dispatchers.IO) {
            val apiJob = launch {
                _allTvSeriesList.value = (repository.getTvSeriesList(Categories.Popular,1) +
                        repository.getTvSeriesList(Categories.Trending,1) +
                        repository.getTvSeriesList(Categories.TopRated,1) +
                        repository.getTvSeriesList(Categories.OnTheAir,1)
                        )
            }
            apiJob.join()
            val roomJob = launch {
                addTvSeriesToRoom()
            }
            roomJob.join()
        }
    }

    private fun getAllMovieList() : List<Movie> {
        val list = mutableListOf<Movie>()

        _allMovieList.value.forEach { movie ->
            list.find { it.id == movie.id }?.let {
                it.category += ("," + movie.category)
            } ?: list.add(movie)
        }

        return list.toList()
    }

    private fun getAllTvSeriesList() : List<TvSeries> {
        val list = mutableListOf<TvSeries>()

        _allTvSeriesList.value.forEach { tvSeries ->
            list.find { it.id == tvSeries.id }?.let {
                it.category += ("," + tvSeries.category)
            } ?: list.add(tvSeries)
        }

        return list.toList()
    }


}