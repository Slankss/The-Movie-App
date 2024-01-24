package com.okankkl.themovieapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.okankkl.themovieapp.api.TmdbApi
import com.okankkl.themovieapp.dao.Dao
import com.okankkl.themovieapp.enum_sealed.Categories
import com.okankkl.themovieapp.enum_sealed.DisplayType
import com.okankkl.themovieapp.enum_sealed.Resources
import com.okankkl.themovieapp.model.Display
import com.okankkl.themovieapp.model.Favourite
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.model.TvSeries
import com.okankkl.themovieapp.paging.data_source.DataSourcesImp
import com.okankkl.themovieapp.paging.paging_source.MoviePagingSource
import com.okankkl.themovieapp.paging.paging_source.TvSeriesPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImp
@Inject
constructor(
    private val tmdbApi: TmdbApi,
    private val dao: Dao
) : Repository
{
    override suspend fun getMoviesFromAPI(category: Categories, page: Int): List<Movie>
    {
        return try
        {
            val response = if (category == Categories.Trending)
            {
                tmdbApi.getTrendingMovies()
            } else
            {
                tmdbApi.getMovies(category.path, 1)
            }

            if (response.isSuccessful)
            {
                response.body()?.results?.map { movie ->
                    movie.category = category.path
                    return@map movie
                } ?: emptyList()
            } else
            {
                emptyList()
            }
        } catch (e: Exception)
        {
            emptyList()
        }
    }

    override suspend fun getDisplaysFromRoom(mediaType: String): List<Display>
    {
        return dao.getDisplays(mediaType)
    }

    override suspend fun addDisplaysToRoom(displayList: List<Display>)
    {
        dao.addDisplays(displayList)
    }

    override suspend fun deleteDisplaysFromRoom(mediaType: String)
    {
        dao.deleteDisplays(mediaType)
    }

    override suspend fun getFavourites(displayType: DisplayType): List<Favourite>
    {
        return dao.getFavourites(displayType.path)
    }

    override suspend fun getFavourite(contentId: Int, displayType: String): Favourite?
    {
        return dao.getFavourite(contentId, displayType)
    }

    override suspend fun addFavourite(favourite: Favourite)
    {
        dao.addFavourite(favourite)
    }

    override suspend fun deleteFavourite(contentId: Int, displayType: String)
    {
        dao.deleteFavourite(contentId, displayType)
    }

    override suspend fun search(query: String): List<Display>
    {
        return try
        {
            val response = tmdbApi.getSearch(query)
            if (response.isSuccessful)
            {
                response.body()?.let { searchResponse ->
                    return@let searchResponse.results
                } ?: emptyList()
            } else
            {
                emptyList()
            }
        } catch (e: Exception)
        {
            emptyList()
        }
    }

    override suspend fun getMovieDetail(id: Int): Resources
    {
        return try
        {
            val response = tmdbApi.getMovie(id = id)
            if (response.isSuccessful)
            {
                response.body()?.let {
                    Resources.Success(data = it)
                } ?: Resources.Failed(errorMsg = "Error")
            } else
            {
                Resources.Failed(errorMsg = "Error")
            }
        } catch (e: Exception)
        {
            Resources.Failed(e.message!!)
        }
    }

    override suspend fun getSimilarMovies(id: Int): List<Movie>
    {
        return try
        {
            val response = tmdbApi.getSimilarMovies(id = 695721)
            if (response.isSuccessful)
            {
                response.body()?.results ?: emptyList()
            } else
            {
                emptyList()
            }
        } catch (e: Exception)
        {
            emptyList()
        }
    }

    override suspend fun getMoviesPage(category: Categories): Flow<PagingData<Movie>>
    {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2
            ),
            pagingSourceFactory = {
                MoviePagingSource(
                    dataSource = DataSourcesImp(tmdbApi),
                    category = category
                )
            }
        ).flow
    }

    override suspend fun getTvSeriesListFromAPI(category: Categories, page: Int): List<TvSeries>
    {
        return try
        {
            val response = if (category == Categories.Trending)
            {
                tmdbApi.getTrendingTvSeries()
            } else
            {
                tmdbApi.getTvSeries(category.path, 1)
            }

            if (response.isSuccessful)
            {
                response.body()?.results?.map { tvSeries ->
                    tvSeries.category = category.path
                    return@map tvSeries
                } ?: emptyList()
            } else
            {
                emptyList()
            }
        } catch (e: Exception)
        {
            emptyList()
        }
    }

    override suspend fun getTvSeriesDetail(id: Int): Resources
    {
        return try
        {
            val response = tmdbApi.getTvSeries(id = id)

            if (response.isSuccessful)
            {
                response.body()?.let { tvSeries ->
                    return@let Resources.Success(data = tvSeries)
                } ?: Resources.Failed("Error")
            } else
            {
                Resources.Failed("Error")
            }
        } catch (e: Exception)
        {
            Resources.Failed(e.localizedMessage!!)
        }
    }

    override suspend fun getSimilarTvSeries(id: Int): List<TvSeries>
    {
        return try
        {
            val response = tmdbApi.getSimilarTvSeries(id = id)

            if (response.isSuccessful)
            {
                response.body()?.results ?: emptyList()
            } else
            {
                emptyList()
            }
        } catch (e: Exception)
        {
            emptyList()
        }
    }

    override suspend fun getTvSeriesPage(category: Categories): Flow<PagingData<TvSeries>>
    {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2
            ),
            pagingSourceFactory = {
                TvSeriesPagingSource(
                    dataSource = DataSourcesImp(tmdbApi),
                    category = category
                )
            }
        ).flow
    }


}

