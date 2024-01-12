package com.okankkl.themovieapp.repository
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.okankkl.themovieapp.api.TmdbApi
import com.okankkl.themovieapp.dao.Dao
import com.okankkl.themovieapp.paging.data_source.DataSourcesImp
import com.okankkl.themovieapp.enum_sealed.Categories
import com.okankkl.themovieapp.enum_sealed.Resources
import com.okankkl.themovieapp.model.Favourite
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.model.TvSeries
import com.okankkl.themovieapp.paging.paging_source.MoviePagingSource
import com.okankkl.themovieapp.paging.paging_source.TvSeriesPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImp
    @Inject
    constructor(
            private val tmdbApi : TmdbApi,
            private val dao : Dao
    ) : Repository
{
    override suspend fun getMovieListFromAPI(category: Categories, page : Int): List<Movie>
    {
        return try {
            val response = if(category == Categories.Trending){
                tmdbApi.getTrendingMovies()
            } else {
                tmdbApi.getMovies(category.path,1)
            }

            if(response.isSuccessful){
                response.body()?.results?.map { movie ->
                    movie.category = ""
                    return@map movie
                }
                    ?: listOf<Movie>()
            }
            else{
                listOf<Movie>()
            }
        } catch (e:Exception){
            listOf<Movie>()
        }
    }

    override suspend fun getMovieListFromRoom(category: Categories): List<Movie>
    {
        return dao.getMovies(category.path,"popularity")
    }

    override suspend fun addMovieListToRoom(movieList: List<Movie>)
    {
        dao.addMovies(movieList)
    }

    override suspend fun deleteMovieListFromRoom()
    {
        dao.deleteMovies()
    }

    override suspend fun getTvSeriesListFromRoom(category: Categories): List<TvSeries>
    {
        return dao.getTvSeries(category.path)
    }

    override suspend fun addTvSeriesListToRoom(tvSeriesList: List<TvSeries>)
    {
        dao.addTvSeries(tvSeriesList)
    }

    override suspend fun deleteTvSeriesListFromRoom()
    {
        dao.deleteTvSeries()
    }

    override suspend fun getFavourites(): List<Favourite>
    {
        return dao.getFavourites()
    }

    override suspend fun addFavourite(favourite: Favourite)
    {
        dao.addFavourite(favourite)
    }

    override suspend fun deleteFavourite(favourite: Favourite)
    {
        dao.deleteFavourite(favourite)
    }

    override suspend fun getMovieDetailFromAPI(id: Int): Resources
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

    override suspend fun getSimilarMoviesFromAPI(id: Int): Resources
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
                    dataSource = DataSourcesImp(tmdbApi),
                    category = category
                )
            }
        ).flow
    }
    
    override suspend fun getTvSeriesList(category: Categories, page : Int): List<TvSeries>
    {
        return try {
            val response = if(category == Categories.Trending){
                tmdbApi.getTrendingTvSeries()
            } else {
                tmdbApi.getTvSeries(category.path,1)
            }

            if(response.isSuccessful){
                response.body()?.results?.map { tvSeries ->
                    tvSeries.category = ""
                    return@map tvSeries
                } ?: listOf()
            }
            else{
                listOf()
            }
        } catch (e:Exception){
            listOf()
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

