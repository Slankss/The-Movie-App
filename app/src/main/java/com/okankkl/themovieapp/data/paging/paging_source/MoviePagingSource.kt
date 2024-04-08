package com.okankkl.themovieapp.data.paging.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.okankkl.themovieapp.data.remote.dto.MovieDto
import com.okankkl.themovieapp.presentation.Categories
import com.okankkl.themovieapp.data.paging.data_source.ContentDataSource
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(
    private val dataSource : ContentDataSource,
    var category : Categories
) : PagingSource<Int, MovieDto>()
{
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDto>
    {
        return try
        {
            val currentPage = params.key ?: 1
            val movies = dataSource.getMovies(
                category = category,
                pageNumber = currentPage
            )
            LoadResult.Page(
                data =  movies.results!!,
                prevKey = if(currentPage == 1) null else currentPage - 1,
                nextKey = if(movies.results.isEmpty()) null else movies.page!! + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
    
    override fun getRefreshKey(state: PagingState<Int, MovieDto>): Int?
    {
        return state.anchorPosition
    }
}