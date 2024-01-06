package com.okankkl.themovieapp.repository
import android.provider.SyncStateContract.Constants
import androidx.paging.Config
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSourceFactory
import com.okankkl.themovieapp.api.TmdbApi
import com.okankkl.themovieapp.paging.data_source.MovieDataSource
import com.okankkl.themovieapp.paging.data_source.MovieDataSourceImp
import com.okankkl.themovieapp.enum_sealed.Categories
import com.okankkl.themovieapp.enum_sealed.Resources
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.paging.paging_source.MoviePagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImp
    @Inject
    constructor(
            private val tmdbApi : TmdbApi
    ) : Repository
{
    override suspend fun getMovieList(category: Categories, page : Int): Resources
    {
        return try {
            val response = if(category == Categories.Trending){
                tmdbApi.getTrendingMovies()
            } else {
                tmdbApi.getMovies(category.path,1)
            }

            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resources.Success(it.results)
                } ?: Resources.Failed("Error")
            }
            else{
               Resources.Failed("Error")
            }
        } catch (e:Exception){
            Resources.Failed(e.localizedMessage!!)
        }
    }

    override suspend fun getMovieDetail(id: Int): Resources
    {
        return try
        {
            val response = tmdbApi.getMovie(id = id)
            if(response.isSuccessful){
                response.body()?.let {
                    Resources.Success(data = it)
                } ?: Resources.Failed(errorMsg = "Error")
            }
            else{
                Resources.Failed(errorMsg = "Error")
            }
        } catch (e : Exception){
            Resources.Failed(e.message!!)
        }
    }

    override suspend fun getSimilarMovies(id: Int): Resources
    {
        return try {
            val response = tmdbApi.getSimilarMovies(id = 695721)

            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resources.Success(it.results)
                } ?: Resources.Failed("Error")
            }
            else{
                Resources.Failed("Error")
            }
        } catch (e:Exception){
            Resources.Failed(e.localizedMessage!!)
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
                    movieDataSource = MovieDataSourceImp(tmdbApi),
                    category = category
                )
            }
        ).flow
    }
    
    override suspend fun getTvSeriesList(category: Categories, page : Int): Resources
    {
        return try {
            val response = if(category == Categories.Trending){
                tmdbApi.getTrendingTvSeries()
            } else {
                tmdbApi.getTvSeries(category.path,1)
            }

            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resources.Success(it.results)
                } ?: Resources.Failed("Error")
            }
            else{
                Resources.Failed("Error")
            }
        } catch (e:Exception){
            Resources.Failed(e.localizedMessage!!)
        }
    }

    override suspend fun getTvSeriesDetail(id: Int): Resources
    {
        return try {
            val response = tmdbApi.getTvSeries(id = id)

            if(response.isSuccessful){
                response.body()?.let { tvSeries ->
                    return@let Resources.Success( data = tvSeries )
                } ?: Resources.Failed("Error")
            }
            else{
                Resources.Failed("Error")
            }
        } catch (e:Exception){
            Resources.Failed(e.localizedMessage!!)
        }
    }

    override suspend fun getSimilarTvSeries(id: Int): Resources
    {
        return try {
            val response = tmdbApi.getSimilarTvSeries(id = id)

            if(response.isSuccessful){
                response.body()?.let { tvSeries ->
                    return@let Resources.Success( data = tvSeries.results )
                } ?: Resources.Failed("Error")
            }
            else{
                Resources.Failed("Error")
            }
        } catch (e:Exception){
            Resources.Failed(e.localizedMessage!!)
        }
    }
}

