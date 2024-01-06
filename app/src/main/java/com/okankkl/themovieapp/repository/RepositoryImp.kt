package com.okankkl.themovieapp.repository
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.okankkl.themovieapp.api.TmdbApi
import com.okankkl.themovieapp.data_source.MovieDataSourceImp
import com.okankkl.themovieapp.enum_sealed.Categories
import com.okankkl.themovieapp.enum_sealed.DataType
import com.okankkl.themovieapp.enum_sealed.Resources
import com.okankkl.themovieapp.paging_source.MoviePagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImp
    @Inject
    constructor(
            private val tmdbApi : TmdbApi
    ) : Repository
{
    override suspend fun getDisplayList(type : DataType,category: Categories, page : Int): Resources
    {
        return try {
            val response = if(category == Categories.Trending){
                tmdbApi.getTrendingdDisplayList(type.path)
            } else {
                tmdbApi.getDisplayList(type.path,category.path,1)
            }
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resources.Success(it.results)
                } ?: Resources.Failed("Body is empty")
            }
            else{
               Resources.Failed("Error")
            }
        } catch (e:Exception){
            Resources.Failed(e.localizedMessage!!)
        }
    }

    override suspend fun getDisplayDetail(type: DataType,id: Int): Resources
    {
        return try
        {
            val response = tmdbApi.getDisplayDetail(type.title,id = id)
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

    override suspend fun getSimilarDisplayList(type: DataType,id: Int): Resources
    {
        return try {
            val response = tmdbApi.getSimilarDisplayList(type.title,id = 695721)

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
    
    override suspend fun getPages(category: Categories): Flow<PagingData<Any>>
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
    

}

