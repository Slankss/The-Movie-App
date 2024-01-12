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
class listViewModel
    @Inject
    constructor(var repository: RepositoryImp, var storeData : StoreData) : ViewModel()
{
    private var _selectedPage = MutableStateFlow(Pages.MovieList)
    var selectedPage = _selectedPage.asStateFlow()

    private var _loadingState = MutableStateFlow(true)
    var loadingState = _loadingState.asStateFlow()

    private var _popularMovies = MutableStateFlow(listOf<Movie>())
    var popularMovies = _popularMovies.asStateFlow()

    private var _trendMovies = MutableStateFlow(listOf<Movie>())
    var trendMovies = _trendMovies.asStateFlow()

    private var _topRatedMovies = MutableStateFlow(listOf<Movie>())
    var topRatedMovies = _topRatedMovies.asStateFlow()

    private var _nowPlayingMovies = MutableStateFlow(listOf<Movie>())
    var nowPlayingMovies = _nowPlayingMovies.asStateFlow()

    private var _popularTvSeries = MutableStateFlow(listOf<TvSeries>())
    var popularTvSeries = _popularTvSeries.asStateFlow()

    private var _trendTvSeries = MutableStateFlow(listOf<TvSeries>())
    var trendTvSeries = _trendTvSeries.asStateFlow()

    private var _topRatedTvSeries = MutableStateFlow(listOf<TvSeries>())
    var topRatedTvSeries = _topRatedTvSeries.asStateFlow()

    private var _onTheAirTvSeries = MutableStateFlow(listOf<TvSeries>())
    var onTheAirTvSeries = _onTheAirTvSeries.asStateFlow()


    var movieUpdateTime : String? = null
    var tvSeriesUpdateTime : String? = null

    fun getMovieList(moviesType: Categories) : List<Movie>?{
        return when(moviesType){
            Categories.Popular -> popularMovies.value
            Categories.Trending -> trendMovies.value
            Categories.TopRated -> topRatedMovies.value
            Categories.NowPlaying -> nowPlayingMovies.value
            else -> null
        }
    }

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
                Log.w("arabam","get movies from api")
                getMoviesFromAPI()
                storeData.saveMovieUpdateTime(currentDate.toString())

            }
            else{
                val lastUpdateTime = LocalDateTime.parse(movieUpdateTime)
                val difference = lastUpdateTime.until(currentDate, ChronoUnit.MINUTES)
                if(difference >= 5){
                    getMoviesFromAPI()
                    Log.w("arabam","get movies from api")
                    storeData.saveMovieUpdateTime(currentDate.toString())
                } else {
                    Log.w("arabam","get movies from Room")
                    getMoviesFromRoom()
                }

            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getMoviesFromRoom(){
        GlobalScope.launch(Dispatchers.IO) {
            _popularMovies.value = repository.getMovieListFromRoom(Categories.Popular)
            _trendMovies.value = repository.getMovieListFromRoom(Categories.Trending)
            _nowPlayingMovies.value = repository.getMovieListFromRoom(Categories.NowPlaying)
            _topRatedMovies.value = repository.getMovieListFromRoom(Categories.TopRated)

        }
    }



    @OptIn(DelicateCoroutinesApi::class)
    private fun addMoviesToRoom(allMovieList : List<Movie>)  {
        GlobalScope.launch(Dispatchers.IO) {
            val deleteJob = launch {
                repository.deleteMovieListFromRoom()
            }
            deleteJob.join()

            val addJob = launch {
                repository.addMovieListToRoom(allMovieList.toList())
            }
            addJob.join()
        }

    }

    @OptIn(DelicateCoroutinesApi::class)
    private suspend fun getMoviesFromAPI(){
        GlobalScope.launch(Dispatchers.IO) {
            val apiJob = launch {
                _popularMovies.value = repository.getMovieListFromAPI(Categories.Popular,1)
                _trendMovies.value = repository.getMovieListFromAPI(Categories.Trending,1)
                _topRatedMovies.value = repository.getMovieListFromAPI(Categories.TopRated,1)
                _nowPlayingMovies.value = repository.getMovieListFromAPI(Categories.NowPlaying,1)
            }
            apiJob.join()
            val roomJob = launch {
                val allMovieList = getAllMovieList()
                addMoviesToRoom(allMovieList)
            }
            roomJob.join()

        }

    }

    private fun getAllMovieList() : List<Movie> {
        val list = mutableListOf<Movie>()

        _popularMovies.value.forEach { movie -> movie.category = Categories.Popular.path }
        _trendMovies.value.forEach { movie -> movie.category = Categories.Trending.path }
        _topRatedMovies.value.forEach { movie -> movie.category = Categories.TopRated.path }
        _nowPlayingMovies.value.forEach { movie -> movie.category = Categories.NowPlaying.path }


        Categories.values().forEach {  category ->
            if(category != Categories.OnTheAir)
            {
                getMovieList(category)?.forEach { movie ->
                    list.find { it.id == movie.id }?.let {
                        it.category += ("," + category.path)
                    } ?: list.add(movie)
                }
            }
        }
        return list.toList()
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
            _popularTvSeries.value = repository.getTvSeriesListFromRoom(Categories.Popular)
            _trendTvSeries.value = repository.getTvSeriesListFromRoom(Categories.Trending)
            _topRatedTvSeries.value = repository.getTvSeriesListFromRoom(Categories.TopRated)
            _onTheAirTvSeries.value = repository.getTvSeriesListFromRoom(Categories.OnTheAir)

        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun addTvSeriesToRoom(){
        GlobalScope.launch(Dispatchers.IO) {
            var allTvSeriesList = mutableListOf<TvSeries>()

            val deleteJob = launch {
                repository.deleteTvSeriesListFromRoom()
            }
            deleteJob.join()

            val changeCategoryJob = launch {
                _popularTvSeries.value.forEach { tvSeries -> tvSeries.category = Categories.Popular.path }
                _trendTvSeries.value.forEach { tvSeries -> tvSeries.category = Categories.Trending.path }
                _topRatedTvSeries.value.forEach { tvSeries -> tvSeries.category = Categories.TopRated.path }
                _onTheAirTvSeries.value.forEach { tvSeries -> tvSeries.category = Categories.OnTheAir.path }
            }
            changeCategoryJob.join()
            val addJob = launch {

                _popularTvSeries.value.forEach { tvSeries ->
                    allTvSeriesList.find { it.id == tvSeries.id }?.let {
                        it.category += ("," + Categories.Popular.path)
                    } ?: allTvSeriesList.add(tvSeries)
                }
                _trendTvSeries.value.forEach { tvSeries ->
                    allTvSeriesList.find { it.id == tvSeries.id }?.let {
                        it.category += ("," + Categories.Trending.path)
                    } ?: allTvSeriesList.add(tvSeries)
                }
                _topRatedTvSeries.value.forEach { tvSeries ->
                    allTvSeriesList.find { it.id == tvSeries.id }?.let {
                        it.category += ("," + Categories.TopRated.path)
                    } ?: allTvSeriesList.add(tvSeries)
                }
                _onTheAirTvSeries.value.forEach { tvSeries ->
                    allTvSeriesList.find { it.id == tvSeries.id }?.let {
                        it.category += ("," + Categories.NowPlaying.path)
                    } ?: allTvSeriesList.add(tvSeries)
                }
                repository.addTvSeriesListToRoom(allTvSeriesList.toList())
            }
            addJob.join()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getTvSeriesFromAPI(){
        GlobalScope.launch(Dispatchers.IO) {
            val apiJob = launch {
                _popularTvSeries.value = repository.getTvSeriesList(Categories.Popular,1)
                _trendTvSeries.value = repository.getTvSeriesList(Categories.Trending,1)
                _topRatedTvSeries.value = repository.getTvSeriesList(Categories.TopRated,1)
                _onTheAirTvSeries.value = repository.getTvSeriesList(Categories.OnTheAir,1)
            }
            apiJob.join()
            val roomJob = launch {
                addTvSeriesToRoom()
            }
            roomJob.join()
        }
    }




}