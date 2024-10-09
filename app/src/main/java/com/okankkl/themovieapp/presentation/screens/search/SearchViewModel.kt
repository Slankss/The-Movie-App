package com.okankkl.themovieapp.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okankkl.themovieapp.utils.Resources
import com.okankkl.themovieapp.domain.model.Content
import com.okankkl.themovieapp.domain.repository.MultiContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val multiContentRepository: MultiContentRepository
) : ViewModel() {
    private var _viewState = MutableStateFlow(SearchState())
    var viewState = _viewState.asStateFlow()

    fun search(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.update { it.copy(isLoading = true) }
            multiContentRepository.search(query, 1).let { result ->
                if (result is Resources.Success) {
                    result.data?.results?.filter { it.backdropPath != null || it.posterPath != null }
                        ?.let { contentList ->
                            _viewState.update {
                                it.copy(
                                    isLoading = false,
                                    message = null,
                                    contentList = contentList,
                                    totalPage = result.data?.totalResults,
                                    currentPage = result.data?.page,
                                    query = query
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

    fun loadMore() {
        viewModelScope.launch(Dispatchers.IO) {
            viewState.value.apply {
                if ((query != null) && (currentPage != null && totalPage != null && currentPage < totalPage)) {
                    _viewState.update { it.copy(isLoading = true) }
                    multiContentRepository.search(query, currentPage + 1).let { result ->
                        if (result is Resources.Success) {
                            result.data?.results?.filter { it.backdropPath != null || it.posterPath != null }
                                ?.let { contentList ->
                                    _viewState.update {
                                        it.copy(
                                            isLoading = false,
                                            message = null,
                                            contentList = it.contentList?.plus(contentList),
                                            totalPage = result.data?.totalPages,
                                            currentPage = result.data?.page,
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
    }
}

data class SearchState(
    val isLoading: Boolean = false,
    val query: String? = null,
    val contentList: List<Content>? = null,
    val totalPage: Int? = null,
    val currentPage: Int? = null,
    val message: String? = null
)