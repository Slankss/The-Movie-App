package com.okankkl.themovieapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okankkl.themovieapp.enum_sealed.Resources
import com.okankkl.themovieapp.repository.MovieRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel
    @Inject
    constructor(var repository: MovieRepositoryImp) : ViewModel()
{
    private var _movieList = MutableStateFlow<Resources>(Resources.Loading)
    var movieList = _movieList.asStateFlow()


    fun getMoviesFromInternet(){
        viewModelScope.launch {
            _movieList.value =  repository.getMovieList()
        }

    }

}