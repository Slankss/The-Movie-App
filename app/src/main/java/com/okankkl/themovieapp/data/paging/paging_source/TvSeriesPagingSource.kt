package com.okankkl.themovieapp.data.paging.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.okankkl.themovieapp.presentation.Categories
import com.okankkl.themovieapp.data.paging.data_source.ContentDataSource
import com.okankkl.themovieapp.data.remote.dto.TvSeriesDto
import retrofit2.HttpException
import java.io.IOException

class TvSeriesPagingSource(
    private val dataSource : ContentDataSource,
    var category : Categories
) : PagingSource<Int, TvSeriesDto>()
{
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvSeriesDto>
    {
        return try
        {
            val currentPage = params.key ?: 1
            val tvSeries = dataSource.getTvSeries(
                category = category,
                pageNumber = currentPage
            )
            LoadResult.Page(
                data =  tvSeries.results!!,
                prevKey = if(currentPage == 1) null else currentPage - 1,
                nextKey = if(tvSeries.results.isEmpty()) null else tvSeries.page!! + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TvSeriesDto>): Int?
    {
        return state.anchorPosition
    }
}