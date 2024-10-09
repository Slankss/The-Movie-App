package com.okankkl.themovieapp.presentation.screens.view_all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okankkl.themovieapp.utils.ContentType
import com.okankkl.themovieapp.utils.FunctionUtils
import com.okankkl.themovieapp.utils.Resources
import com.okankkl.themovieapp.data.mapper.toContent
import com.okankkl.themovieapp.domain.model.Content
import com.okankkl.themovieapp.domain.repository.MoviesRepository
import com.okankkl.themovieapp.domain.repository.TvSeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewAllViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val tvSeriesRepository: TvSeriesRepository
) : ViewModel() {
    private val _viewState = MutableStateFlow(ViewAllState())
    val viewState = _viewState.asStateFlow()

    fun load(type: String, category: String) {
        when (FunctionUtils.getType(type)) {
            ContentType.TvSeries -> loadTvSeries(category)
            ContentType.Movie -> loadMovies(category)
        }
    }

    private fun loadMovies(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            viewState.value.apply {
                if (currentPage == null || totalPage == null || currentPage < totalPage) {
                    _viewState.update { it.copy(isLoading = true) }
                    moviesRepository.getRemoteMovies(
                        category = FunctionUtils.getCategory(category),
                        page = (viewState.value.currentPage ?: 0 ) + 1
                    ).let { result ->
                        if (result is Resources.Success) {
                            result.data?.results?.map { movie ->
                                movie.toContent()
                            }?.filter { it.posterPath != null || it.backdropPath != null }
                                ?.let { movies ->
                                    _viewState.update {
                                        it.copy(
                                            isLoading = false,
                                            message = null,
                                            contentList = if (it.currentPage == null) movies else it.contentList?.plus(movies),
                                            currentPage = result.data?.page,
                                            totalPage = result.data?.totalPages
                                        )
                                    }
                                }
                        }
                    }
                }
            }
        }
    }

    private fun loadTvSeries(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.update { it.copy(isLoading = true) }
            tvSeriesRepository.getRemoteTvSeries(
                category = FunctionUtils.getCategory(category),
                page = viewState.value.currentPage ?: 1
            ).let { result ->
                if (result is Resources.Success) {
                    result.data?.results?.map { tvSeries ->
                        tvSeries.toContent()
                    }?.let { tvSeriesList ->
                        _viewState.update {
                            it.copy(
                                isLoading = false,
                                message = null,
                                contentList = if (viewState.value.currentPage == null) tvSeriesList else it.contentList?.plus(
                                    tvSeriesList
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

data class ViewAllState(
    val isLoading: Boolean = false,
    val contentList: List<Content>? = null,
    val totalPage: Int? = null,
    val currentPage: Int? = null,
    val message: String? = null
)