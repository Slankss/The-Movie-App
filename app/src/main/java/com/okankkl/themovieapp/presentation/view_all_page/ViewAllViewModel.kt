package com.okankkl.themovieapp.presentation.view_all_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.okankkl.themovieapp.presentation.Categories
import com.okankkl.themovieapp.presentation.DisplayType
import com.okankkl.themovieapp.domain.model.Movie
import com.okankkl.themovieapp.domain.model.TvSeries
import com.okankkl.themovieapp.data.paging.use_case.GetMoviesUseCase
import com.okankkl.themovieapp.data.paging.use_case.GetTvSeriesUseCase
import com.okankkl.themovieapp.data.remote.dto.MovieDto
import com.okankkl.themovieapp.data.remote.dto.TvSeriesDto
import com.okankkl.themovieapp.data.remote.repository.ApiRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewAllViewModel
    @Inject
    constructor(
        val repository : ApiRepositoryImp,
        val getMoviesUseCase: GetMoviesUseCase,
        val getTvSeriesUseCase: GetTvSeriesUseCase
        ) : ViewModel()
{

    private val _movieState : MutableStateFlow<PagingData<MovieDto>> = MutableStateFlow(PagingData.empty())
    val movieState get() = _movieState

    private val _tvSeriesState : MutableStateFlow<PagingData<TvSeriesDto>> = MutableStateFlow(PagingData.empty())
    val tvSeriesState get() = _tvSeriesState

    fun load(type: String, category: String){
        when(getType(type)){
            DisplayType.TvSeries -> loadTvSeries(category)
            DisplayType.Movie -> loadMovies(category)
        }
    }
    
    private fun loadMovies(category : String){
        viewModelScope.launch {
            getMoviesUseCase.execute(
                category = getCategory(category),
                input = Unit)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect{
                    _movieState.value = it
                }
        }
    }

    private fun loadTvSeries(category: String){
        viewModelScope.launch {
            getTvSeriesUseCase.execute(
                category = getCategory(category),
                input = Unit)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect{
                    _tvSeriesState.value = it
                }
        }
    }

    fun getType(type: String) : DisplayType {
        return when(type){
            DisplayType.Movie.path -> DisplayType.Movie
            else -> DisplayType.TvSeries
        }
    }
    fun getCategory(category: String) : Categories {
        return when(category){
            Categories.Trending.path -> Categories.Trending
            Categories.TopRated.path -> Categories.TopRated
            Categories.NowPlaying.path -> Categories.NowPlaying
            Categories.OnTheAir.path -> Categories.OnTheAir
            else -> Categories.Popular
        }
    }

}