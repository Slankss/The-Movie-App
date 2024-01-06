package com.okankkl.themovieapp.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.okankkl.themovieapp.data_source.MovieDataSource
import com.okankkl.themovieapp.enum_sealed.Categories
import com.okankkl.themovieapp.model.Movie
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

class MoviePagingSource(
    private val movieDataSource : MovieDataSource,
    var category : Categories
) : PagingSource<Int, Any>()
{
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Any>
    {
        return try
        {
            val currentPage = params.key ?: 1
            val response = movieDataSource.getMovies(
                category = category,
                pageNumber = currentPage
            )
            LoadResult.Page(
                data =  response.results!!,
                prevKey = if(currentPage == 1) null else currentPage - 1,
                nextKey = if(response.results.isEmpty()) null else response.page!! + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
    
    override fun getRefreshKey(state: PagingState<Int, Any>): Int?
    {
        return state.anchorPosition
    }
}