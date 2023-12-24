package com.okankkl.themovieapp.viewModel

import androidx.lifecycle.ViewModel
import com.okankkl.themovieapp.api.MovieApi
import com.okankkl.themovieapp.model.Resources
import com.okankkl.themovieapp.repository.MovieRepository
import com.okankkl.themovieapp.repository.MovieRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel
    @Inject
    constructor(var repository: MovieRepositoryImp) : ViewModel()
{
    private var _movieList = MutableStateFlow<Resources>(Resources.Loading)
    var movieList = _movieList.asStateFlow()


    fun getMoviesFromInternet(){
        _movieList.update { Resources.Loading }
        _movieList.update { repository.getMovieList() }

    }

}