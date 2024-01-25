package com.okankkl.themovieapp.paging.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.okankkl.themovieapp.enum_sealed.Categories
import com.okankkl.themovieapp.model.TvSeries
import com.okankkl.themovieapp.paging.data_source.DataSources
import retrofit2.HttpException
import java.io.IOException

class TvSeriesPagingSource(
    private val dataSource : DataSources,
    var category : Categories
) : PagingSource<Int, TvSeries>()
{
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvSeries>
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

    override fun getRefreshKey(state: PagingState<Int, TvSeries>): Int?
    {
        return state.anchorPosition
    }
}