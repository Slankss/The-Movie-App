package com.okankkl.themovieapp.presentation.screens.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okankkl.themovieapp.utils.Categories
import com.okankkl.themovieapp.utils.Resources
import com.okankkl.themovieapp.data.model.dto.Movie
import com.okankkl.themovieapp.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewMoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(NewMoviesState())
    val viewState = _viewState.asStateFlow()

    fun loadMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.update { it.copy(isLoading = true) }
            moviesRepository.getRemoteMovies(
                category = Categories.UpComing,
                page = (viewState.value.currentPage ?: 0) + 1
            )
                .let { result ->
                    if (result is Resources.Success) {
                        result.data?.results?.filter { it.posterPath != null || it.backdropPath != null }
                            ?.let { movies ->
                                _viewState.update {
                                    it.copy(
                                        isLoading = false,
                                        movies = if(it.currentPage == null) movies else it.movies?.plus(movies),
                                        totalPages = result.data?.totalResults,
                                        currentPage = result.data?.page,
                                        message = null
                                    )
                                }
                            }
                    } else {
                        _viewState.update {
                            it.copy(
                                isLoading = false,
                                message = result.message
                            )
                        }
                    }
                }
        }
    }
}

data class NewMoviesState(
    var isLoading: Boolean? = null,
    var movies: List<Movie>? = null,
    val totalPages: Int? = null,
    val currentPage: Int? = null,
    var message: String? = null
)