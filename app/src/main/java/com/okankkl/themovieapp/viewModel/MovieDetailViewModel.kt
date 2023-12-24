package com.okankkl.themovieapp.viewModel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.model.Resources
import com.okankkl.themovieapp.repository.MovieRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel
    @Inject
    constructor(var repository: MovieRepositoryImp
) : ViewModel()
{
    private val _movie = MutableStateFlow<Resources>(Resources.Loading)
    var movie = _movie.asStateFlow()

    fun getMovie(id : Int){
        viewModelScope.launch {
            _movie.update { Resources.Loading }
            _movie.update { repository.getMovieDetail(id)}
        }
    }

}