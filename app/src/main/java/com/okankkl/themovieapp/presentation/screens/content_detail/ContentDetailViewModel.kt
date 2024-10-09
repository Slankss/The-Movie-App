package com.okankkl.themovieapp.presentation.screens.content_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okankkl.themovieapp.utils.ContentType
import com.okankkl.themovieapp.utils.Resources
import com.okankkl.themovieapp.data.mapper.toContent
import com.okankkl.themovieapp.data.mapper.toContentDetail
import com.okankkl.themovieapp.data.mapper.toFavourite
import com.okankkl.themovieapp.data.model.dto.Person
import com.okankkl.themovieapp.data.model.dto.Review
import com.okankkl.themovieapp.data.model.entity.Favourite
import com.okankkl.themovieapp.domain.model.Content
import com.okankkl.themovieapp.domain.model.ContentDetail
import com.okankkl.themovieapp.domain.repository.FavouritesRepository
import com.okankkl.themovieapp.domain.repository.MoviesRepository
import com.okankkl.themovieapp.domain.repository.PersonRepository
import com.okankkl.themovieapp.domain.repository.TvSeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentDetailViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val tvSeriesRepository: TvSeriesRepository,
    private val favouritesRepository: FavouritesRepository,
    private val personRepository: PersonRepository
) : ViewModel() {
    private val _viewState = MutableStateFlow(ContentDetailState())
    var viewState = _viewState.asStateFlow()

    fun getData(contentId: Int, contentType: String) {
        if (contentType == ContentType.Movie.path) {
            getMovieDetail(contentId)
            getMovieReviews(contentId)
        } else {
            getTvSeriesDetail(contentId)
            getTvSeriesReviews(contentId)
        }
        getFavourite(contentId, contentType)
    }

    private fun getMovieDetail(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.update { it.copy(isLoading = true) }
            moviesRepository.getMovieDetail(id).let { result ->
                if (result is Resources.Success) {
                    _viewState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                            contentDetail = result.data?.toContentDetail()
                        )
                    }
                } else {
                    _viewState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }

    private fun getTvSeriesDetail(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.update { it.copy(isLoading = true) }
            tvSeriesRepository.getTvSeriesDetail(id).let { result ->
                if (result is Resources.Success) {
                    _viewState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                            contentDetail = result.data?.toContentDetail()
                        )
                    }
                } else {
                    _viewState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }

    private fun getFavourite(id: Int, contentType: String) {
        viewModelScope.launch(Dispatchers.IO) {
            favouritesRepository.getFavourite(id, contentType).let { result ->
                if (result is Resources.Success && result.data != null) {
                    _viewState.update {
                        it.copy(
                            isFavourite = true
                        )
                    }
                }
            }
        }
    }

    fun addFavourite() {
        viewModelScope.launch(Dispatchers.IO) {
            viewState.value.contentDetail?.let { contentDetail ->
                favouritesRepository.addFavourite(contentDetail.toFavourite()).let { result ->
                    if (result is Resources.Success && (result.data?.toInt() ?: -1) >= 0) {
                        _viewState.update {
                            it.copy(
                                isFavourite = true
                            )
                        }
                    }
                }
            }
        }
    }

    fun deleteFavourite() {
        viewModelScope.launch(Dispatchers.IO) {
            viewState.value.contentDetail?.apply {
                if (id != null && contentType != null){
                    favouritesRepository.deleteFavourite(id, contentType).let { result ->
                        if (result is Resources.Success) {
                            _viewState.update {
                                it.copy(
                                    isFavourite = false
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun getMovieReviews(contendId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            viewState.value.apply {
                reviewsState.apply {
                    if (currentPage == null || totalPage == null || currentPage <= totalPage) {
                        moviesRepository.getReviews(contendId, (currentPage ?: 0) + 1).let { result ->
                            if (result is Resources.Success) {
                                _viewState.update { viewState ->
                                    viewState.copy(
                                        reviewsState = viewState.reviewsState.copy(
                                            currentPage = 1,
                                            totalPage = result.data?.totalPages,
                                            reviews = result.data?.results?.let {
                                                viewState.reviewsState.reviews?.plus(
                                                    it
                                                )
                                            } ?: result.data?.results
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun getTvSeriesReviews(contentId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            viewState.value.apply {
                reviewsState.apply {
                    if (currentPage == null || totalPage == null || currentPage <= totalPage) {
                        tvSeriesRepository.getReviews(contentId, (currentPage ?:0) + 1).let { result ->
                            if (result is Resources.Success) {
                                _viewState.update { viewState ->
                                    viewState.copy(
                                        reviewsState = viewState.reviewsState.copy(
                                            currentPage = 1,
                                            totalPage = result.data?.totalPages,
                                            reviews = result.data?.results?.let {
                                                viewState.reviewsState.reviews?.plus(
                                                    it
                                                )
                                            } ?: result.data?.results
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun changeVideosBottomSheetState(state: Boolean) {
        _viewState.update {
            it.copy(
                showVideosBottomSheet = state
            )
        }
    }

    fun changeCommentsBottomSheetState(state: Boolean) {
        _viewState.update {
            it.copy(
                showReviewsBottomSheet = state
            )
        }
    }

    fun changeVideoDialogState(state: Boolean) {
        _viewState.update {
            it.copy(
                showVideoDialog = state
            )
        }
    }

    fun setCurrentVideoKey(key: String?) {
        _viewState.update {
            it.copy(
                currentVideoKey = key
            )
        }
    }

    fun getPersonDetail(id: Int) {
        if (viewState.value.personDetail?.id == id){
            changePersonDetailBottomSheetState(true)
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                personRepository.getPersonDetail(id).let { result ->
                    if (result is Resources.Success) {
                        _viewState.update {
                            it.copy(
                                showPersonDetailBottomSheet = true,
                                personDetail = result.data
                            )
                        }
                    }
                }
            }
        }
    }

    fun changePersonDetailBottomSheetState(state: Boolean) {
        _viewState.update {
            it.copy(
                showPersonDetailBottomSheet = state
            )
        }
    }

    fun changeImageDialogState(state: Boolean){
        _viewState.update {
            it.copy(
                showImageDialog = state
            )
        }
    }
}

data class ContentDetailState(
    val isLoading: Boolean = false,
    val isFavourite: Boolean = false,
    val contentDetail: ContentDetail? = null,
    val errorMessage: String? = null,
    val showVideosBottomSheet: Boolean = false,
    val showReviewsBottomSheet: Boolean = false,
    val showVideoDialog: Boolean = false,
    val currentVideoKey: String? = null,
    val currentVideoSecond: Int? = null,
    val reviewsState: ReviewsState = ReviewsState(),
    val personDetail: Person? = null,
    val showPersonDetailBottomSheet: Boolean = false,
    val showImageDialog: Boolean = false
)

data class ReviewsState(
    val currentPage: Int? = null,
    val totalPage: Int? = null,
    val reviews: List<Review>? = null
)