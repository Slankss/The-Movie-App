package com.okankkl.themovieapp.presentation.screens.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okankkl.themovieapp.utils.ContentType
import com.okankkl.themovieapp.utils.Resources
import com.okankkl.themovieapp.data.model.entity.Favourite
import com.okankkl.themovieapp.domain.repository.FavouritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val favouritesRepository: FavouritesRepository
) : ViewModel() {
    private var _viewState = MutableStateFlow(FavouriteState())
    val viewState = _viewState.asStateFlow()

    fun setPage(contentType: ContentType) {
        _viewState.update {
            it.copy(
                selectedPage = contentType
            )
        }
    }

    fun getFavourites() {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.update { it.copy(isLoading = true) }
            favouritesRepository.getFavourites().let { result ->
                if (result is Resources.Success) {
                    _viewState.update { viewState ->
                        viewState.copy(
                            isLoading = false,
                            favouriteMovies = result.data?.filter { it.contentType == ContentType.Movie.path },
                            favouriteTvSeries = result.data?.filter { it.contentType == ContentType.TvSeries.path },
                            message = null
                        )
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

    fun deleteFavourite(favourite: Favourite) {
        viewModelScope.launch(Dispatchers.IO) {
            favourite.apply {
                if (contentId != null && contentType != null){
                    favouritesRepository.deleteFavourite(contentId!!, contentType!!).let { result ->
                        if (result is Resources.Success) {
                            _viewState.update { viewState ->
                                if (favourite.contentType == ContentType.Movie.path) {
                                    viewState.copy(
                                        favouriteMovies = viewState.favouriteMovies?.filter { it != favourite }
                                    )
                                } else {
                                    viewState.copy(
                                        favouriteTvSeries = viewState.favouriteTvSeries?.filter { it != favourite }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}

data class FavouriteState(
    val isLoading: Boolean = false,
    val favouriteMovies: List<Favourite>? = null,
    val favouriteTvSeries: List<Favourite>? = null,
    val selectedPage: ContentType? = null,
    val message: String? = null,

)