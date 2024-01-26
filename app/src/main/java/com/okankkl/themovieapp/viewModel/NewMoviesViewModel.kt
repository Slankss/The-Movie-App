package com.okankkl.themovieapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.okankkl.themovieapp.enum_sealed.Categories
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.paging.use_case.GetMoviesUseCase
import com.okankkl.themovieapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewMoviesViewModel
    @Inject
    constructor(
        val repository: Repository,
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
                    _newsMovies.value = it
                }
        }
    }


}