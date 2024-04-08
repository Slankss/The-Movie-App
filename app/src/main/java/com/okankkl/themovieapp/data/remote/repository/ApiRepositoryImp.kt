package com.okankkl.themovieapp.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.okankkl.themovieapp.data.remote.TmdbApi
import com.okankkl.themovieapp.data.remote.dto.MovieDto
import com.okankkl.themovieapp.presentation.Categories
import com.okankkl.themovieapp.data.remote.dto.toContentResponse
import com.okankkl.themovieapp.data.remote.dto.toMovie
import com.okankkl.themovieapp.data.remote.dto.toMultiSearchContentResponse
import com.okankkl.themovieapp.data.remote.dto.toTvSeries
import com.okankkl.themovieapp.domain.model.Movie
import com.okankkl.themovieapp.domain.model.TvSeries
import com.okankkl.themovieapp.domain.model.response.ContentResponse
import com.okankkl.themovieapp.domain.model.response.MultiSearchContentResponse
import com.okankkl.themovieapp.domain.repository.ApiRepository
import com.okankkl.themovieapp.data.paging.data_source.ContentDataSourceImp
import com.okankkl.themovieapp.data.paging.paging_source.MoviePagingSource
import com.okankkl.themovieapp.data.paging.paging_source.TvSeriesPagingSource
import com.okankkl.themovieapp.data.remote.dto.TvSeriesDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApiRepositoryImp
@Inject
constructor(
    private val tmdbApi: TmdbApi
) : ApiRepository
{
    override suspend fun getMovies(category: Categories, page: Int): ContentResponse
    {
        if(category == Categories.Trending)
            return tmdbApi.getTrendingMovies().toContentResponse()

        return tmdbApi.getMovies(category.path,1).toContentResponse()
    }
    override suspend fun search(query: String): MultiSearchContentResponse
    {
        return tmdbApi.getSearch(query).toMultiSearchContentResponse()
    }
    override suspend fun getMovieDetail(id: Int): Movie
    {
        return tmdbApi.getMovie(id).toMovie()
    }
    override suspend fun getSimilarMovies(id: Int): ContentResponse
    {
        return tmdbApi.getSimilarMovies(id).toContentResponse()
    }
    override suspend fun getMoviesPage(category: Categories): Flow<PagingData<MovieDto>>
    {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2
            ),
            pagingSourceFactory = {
                MoviePagingSource(
                    dataSource = ContentDataSourceImp(tmdbApi),
                    category = category
                )
            }
        ).flow
    }

    override suspend fun getTvSeries(category: Categories, page: Int): ContentResponse
    {
        if(category == Categories.Trending)
            return tmdbApi.getTrendingTvSeries().toContentResponse()

        return tmdbApi.getTvSeriesList(category.path,page).toContentResponse()
    }
    override suspend fun getTvSeriesDetail(id: Int): TvSeries
    {
        return tmdbApi.getTvSeries(id).toTvSeries()
    }
    override suspend fun getSimilarTvSeries(id: Int): ContentResponse
    {
        return tmdbApi.getSimilarTvSeries(id).toContentResponse()
    }
    override suspend fun getTvSeriesPage(category: Categories): Flow<PagingData<TvSeriesDto>>
    {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2
            ),
            pagingSourceFactory = {
                TvSeriesPagingSource(
                    dataSource = ContentDataSourceImp(tmdbApi),
                    category = category
                )
            }
        ).flow
    }
}

