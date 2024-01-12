package com.okankkl.themovieapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okankkl.themovieapp.enum_sealed.Resources
import com.okankkl.themovieapp.repository.RepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel
    @Inject
    constructor(var repository: RepositoryImp
) : ViewModel()
{
    private val _movie = MutableStateFlow<Resources>(Resources.Loading)
    var movie = _movie.asStateFlow()

    private val _similarMovies = MutableStateFlow<Resources>(Resources.Loading)
    var similarMovies = _similarMovies.asStateFlow()

    fun getMovie(id : Int){
        viewModelScope.launch {
            _movie.value = repository.getMovieDetailFromAPI(id)
            _similarMovies.value = repository.getSimilarMoviesFromAPI(id)
        }
    }

}