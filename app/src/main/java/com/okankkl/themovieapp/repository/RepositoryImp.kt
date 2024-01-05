package com.okankkl.themovieapp.repository
import com.okankkl.themovieapp.api.api
import com.okankkl.themovieapp.enum_sealed.Categories
import com.okankkl.themovieapp.enum_sealed.Resources
import javax.inject.Inject

class RepositoryImp
    @Inject
    constructor(var api : api) : Repository
{
    override suspend fun getMovieList(category: Categories, page : Int): Resources
    {
        return try {
            val response = if(category == Categories.Trending){
                api.getTrendingMovies()
            } else {
                api.getMovies(category.path,1)
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
            val response = api.getMovie(id = id)
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
            val response = api.getSimilarMovies(id = 695721)

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

    override suspend fun getTvSeriesList(category: Categories, page : Int): Resources
    {
        return try {
            val response = if(category == Categories.Trending){
                api.getTrendingTvSeries()
            } else {
                api.getTvSeries(category.path,1)
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
            val response = api.getTvSeries(id = id)

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
            val response = api.getSimilarTvSeries(id = id)

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

