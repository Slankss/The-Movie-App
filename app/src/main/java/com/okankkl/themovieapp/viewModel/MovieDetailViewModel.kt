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
class MovieDetailViewModel
    @Inject
    constructor(var repository: MovieRepositoryImp
) : ViewModel()
{
    private val _movie = MutableStateFlow<Resources>(Resources.Loading)
    var movie = _movie.asStateFlow()

    fun getMovie(id : Int){
        viewModelScope.launch {
            _movie.value = repository.getMovieDetail(id)
        }
    }

}