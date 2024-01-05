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
) : PagingSource<Int, Movie>()
{
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie>
    {
        return try
        {
            val currentPage = params.key ?: 1
            val movies = movieDataSource.getMovies(
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
    
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int?
    {
        return state.anchorPosition
    }
}