package com.okankkl.themovieapp.presentation.news_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.okankkl.themovieapp.presentation.Categories
import com.okankkl.themovieapp.domain.model.Movie
import com.okankkl.themovieapp.paging.use_case.GetMoviesUseCase
import com.okankkl.themovieapp.domain.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class NewMoviesViewModel
    @Inject
    constructor(
        val apiRepository: ApiRepository,
        val getMoviesUseCase: GetMoviesUseCase
    )
    : ViewModel()
{

    private val _newsMovies : MutableStateFlow<PagingData<Movie>> = MutableStateFlow(PagingData.empty())
    val newsMovies get() = _newsMovies

    fun loadMovies(){
        viewModelScope.launch {
            getMoviesUseCase.execute(
                category = Categories.UpComing,
                input = Unit)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect{
                    _newsMovies.value = it.filter {  movie ->
                        val localDate = LocalDate.now()
                        val movieDate = LocalDate.parse(movie.releaseDate)
                        movieDate.isAfter(localDate)
                    }
                }
        }

    }


}