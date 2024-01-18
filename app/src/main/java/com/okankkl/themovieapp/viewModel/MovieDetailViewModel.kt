package com.okankkl.themovieapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okankkl.themovieapp.enum_sealed.DisplayType
import com.okankkl.themovieapp.enum_sealed.Resources
import com.okankkl.themovieapp.model.Favourite
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.repository.RepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
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

    private val _favouriteState = MutableStateFlow<Favourite?>(null)
    val favouriteState = _favouriteState.asStateFlow()

    fun getMovie(id : Int){
        viewModelScope.launch {
            _movie.value = repository.getMovieDetailFromAPI(id)
            _similarMovies.value = repository.getSimilarMoviesFromAPI(id)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getFavourite(contentId : Int){
        GlobalScope.launch(Dispatchers.IO){
            _favouriteState.value = repository.getFavourite(contentId)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun addFavourite(movie : Movie){
        GlobalScope.launch(Dispatchers.IO) {
            val addJob = launch {
                val favourite = Favourite(DisplayType.Movie.path,movie.title,movie.backdropPath ?: "",movie.posterPath ?: "",movie.id)
                repository.addFavourite(favourite)
            }
            addJob.invokeOnCompletion {
                getFavourite(movie.id)
            }

        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun deleteFavourite(movie: Movie){
        GlobalScope.launch(Dispatchers.IO){
            val deleteJob = launch {
                repository.deleteFavourite(movie.id)
            }
            deleteJob.invokeOnCompletion {
                _favouriteState.value = null
            }
        }
    }

}