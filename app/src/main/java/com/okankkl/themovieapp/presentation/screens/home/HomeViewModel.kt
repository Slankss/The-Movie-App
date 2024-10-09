package com.okankkl.themovieapp.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okankkl.themovieapp.utils.Categories
import com.okankkl.themovieapp.utils.Constants.MOVIE_UPDATE_TIME
import com.okankkl.themovieapp.utils.Constants.TV_SERIES_UPDATE_TIME
import com.okankkl.themovieapp.utils.ContentType
import com.okankkl.themovieapp.utils.Resources
import com.okankkl.themovieapp.utils.moviesCategoryList
import com.okankkl.themovieapp.utils.toLocalDateTime
import com.okankkl.themovieapp.utils.tvSeriesCategoryList
import com.okankkl.themovieapp.data.local.preferences.PrefsImpl
import com.okankkl.themovieapp.data.mapper.toContent
import com.okankkl.themovieapp.data.model.dto.Movie
import com.okankkl.themovieapp.data.model.dto.TvSeries
import com.okankkl.themovieapp.domain.model.Content
import com.okankkl.themovieapp.domain.repository.MoviesRepository
import com.okankkl.themovieapp.domain.repository.TvSeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val prefs: PrefsImpl,
    private val moviesRepository: MoviesRepository,
    private val tvSeriesRepository: TvSeriesRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(HomeState())
    val viewState: StateFlow<HomeState> = _viewState.asStateFlow()

    fun setSelectedPage(page: ContentType?) {
        if (viewState.value.selectedPage == null || viewState.value.selectedPage != page){
            if (page == ContentType.TvSeries){
                getTvSeries()
            } else {
                getMovies()
            }
            _viewState.update {
                it.copy(
                    selectedPage = page ?: ContentType.Movie
                )
            }
        }
    }

    private fun getMovies() {
        _viewState.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            prefs.getStringValue(MOVIE_UPDATE_TIME).firstOrNull().let { movieUpdateTime ->
                val difference = movieUpdateTime.toLocalDateTime()?.until(LocalDateTime.now(), ChronoUnit.DAYS)
                var movies : List<Content>? = null
                if (difference == null || difference >= 1) {

                    getRemoteMovies().let { remoteMovies ->
                        if(remoteMovies.isNotEmpty()){
                            prefs.setStringValue(
                                MOVIE_UPDATE_TIME,
                                LocalDateTime.now().toString()
                            )
                            moviesRepository.deleteLocalMovies()
                            moviesRepository.addMoviesToLocal(remoteMovies)
                            movies = remoteMovies.map { it.toContent() }
                        } else {
                            getLocalMovies().let { localMovies ->
                                if(localMovies.isNotEmpty()){
                                    movies = localMovies
                                }
                            }
                        }
                    }
                } else {
                    getLocalMovies().let { localMovies ->
                        movies = localMovies
                    }
                }
                if (movies.isNullOrEmpty()){
                    _viewState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "No Data"
                        )
                    }
                } else {
                    _viewState.update { viewState ->
                        viewState.copy(
                            isLoading = false,
                            movies = ContentList(
                                popular = movies?.filter { it.category == Categories.Popular.path },
                                topRated = movies?.filter { it.category == Categories.TopRated.path },
                                trending = movies?.filter { it.category == Categories.Trending.path },
                                nowPlaying = movies?.filter { it.category == Categories.NowPlaying.path }
                            ),
                            errorMessage = null
                        )
                    }
                }
            }
        }
    }

    private suspend fun getRemoteMovies(): List<Movie> {
        val movieList = mutableListOf<Movie>()
        moviesCategoryList.forEach { category ->
            moviesRepository.getRemoteMovies(category, 1).let { result ->
                if (result is Resources.Success) {
                    result.data?.results
                        ?.map { it.copy(category = category.path) }
                        ?.filter { it.posterPath != null }
                        ?.let { movies ->
                            movieList.addAll(movies)
                        }
                }
            }
        }
        return movieList
    }

    private suspend fun getLocalMovies() : List<Content> {
        moviesRepository.getLocalMovies().let { result ->
            if (result is Resources.Success) {
                return result.data?.map { it.toContent() } ?: emptyList()
            }
        }
        return emptyList()
    }

    private fun getTvSeries() {
        _viewState.update {
            it.copy(
                isLoading = true,
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            prefs.getStringValue(TV_SERIES_UPDATE_TIME).firstOrNull().let { tvSeriesUpdateTime ->
                val difference = tvSeriesUpdateTime.toLocalDateTime()?.until(LocalDateTime.now(), ChronoUnit.DAYS)
                var tvSeries : List<Content>? = null
                if (difference == null || difference >= 1) {
                    getRemoteTvSeries().let { remoteTvSeries ->
                        if(remoteTvSeries.isNotEmpty()){
                            prefs.setStringValue(
                                TV_SERIES_UPDATE_TIME,
                                LocalDateTime.now().toString()
                            )
                            tvSeriesRepository.deleteLocalTvSeries()
                            tvSeriesRepository.addTvSeriesToLocal(remoteTvSeries)
                            tvSeries = remoteTvSeries.map { it.toContent() }
                        } else {
                            getLocalTvSeries().let { localTvSeries ->
                                if(localTvSeries.isNotEmpty()){
                                    tvSeries = localTvSeries
                                }
                            }
                        }
                    }
                } else {
                    getLocalTvSeries().let { localTvSeries ->
                        tvSeries = localTvSeries
                    }
                }
                if (tvSeries.isNullOrEmpty()){
                    _viewState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "No Data"
                        )
                    }
                } else {
                    _viewState.update { viewState ->
                        viewState.copy(
                            isLoading = false,
                            tvSeries = ContentList(
                                popular = tvSeries?.filter { it.category == Categories.Popular.path },
                                topRated = tvSeries?.filter { it.category == Categories.TopRated.path },
                                trending = tvSeries?.filter { it.category == Categories.Trending.path },
                                nowPlaying = tvSeries?.filter { it.category == Categories.OnTheAir.path }
                            ),
                            errorMessage = null
                        )
                    }
                }
            }
        }
    }

    private suspend fun getRemoteTvSeries(): List<TvSeries> {
        val tvSeriesList = mutableListOf<TvSeries>()
        tvSeriesCategoryList.forEach { category ->
            tvSeriesRepository.getRemoteTvSeries(category, 1).let { result ->
                if (result is Resources.Success) {
                    result.data?.results?.map { it.copy(category = category.path) }
                        ?.filter { it.posterPath != null }
                        ?.let { tvSeries ->
                            tvSeriesList.addAll(tvSeries)
                        }
                }
            }
        }
        return tvSeriesList
    }

    private suspend fun getLocalTvSeries() : List<Content> {
        tvSeriesRepository.getLocalTvSeries().let { result ->
            if (result is Resources.Success) {
                return result.data?.map { it.toContent() } ?: emptyList()
            }
        }
        return emptyList()
    }

}

data class HomeState(
    val isLoading: Boolean = false,
    val selectedPage: ContentType? = null,
    val movies: ContentList? = null,
    val tvSeries: ContentList? = null,
    val currentTrendMovie: Int? = null,
    val currentTrendTvSeries: Int? = null,
    val errorMessage: String? = null
)

data class ContentList(
    var popular : List<Content>? = null,
    var topRated : List<Content>? = null,
    var trending : List<Content>? = null,
    var nowPlaying : List<Content>? = null,
)