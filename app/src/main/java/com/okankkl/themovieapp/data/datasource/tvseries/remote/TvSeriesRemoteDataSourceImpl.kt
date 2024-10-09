package com.okankkl.themovieapp.data.datasource.tvseries.remote

import com.okankkl.themovieapp.data.model.dto.Review
import com.okankkl.themovieapp.data.remote.TmdbApi
import com.okankkl.themovieapp.utils.Categories
import com.okankkl.themovieapp.utils.Resources
import com.okankkl.themovieapp.utils.apiCall
import com.okankkl.themovieapp.data.model.dto.TvSeries
import com.okankkl.themovieapp.data.response.ResponseDto
import javax.inject.Inject

class TvSeriesRemoteDataSourceImpl @Inject constructor(
    private val api: TmdbApi
) : TvSeriesRemoteDataSource {

    override suspend fun getTvSeries(
        category: Categories,
        page: Int
    ): Resources<ResponseDto<List<TvSeries>>> {
        return apiCall { api.getTvSeriesList(category.path, page) }
    }

    override suspend fun getTrendingTvSeries(): Resources<ResponseDto<List<TvSeries>>> {
        return apiCall { api.getTrendingTvSeries() }
    }

    override suspend fun getTvDetail(id: Int): Resources<TvSeries> {
        return apiCall { api.getTvSeries(id) }
    }

    override suspend fun getSimilarTvSeries(id: Int): Resources<ResponseDto<List<TvSeries>>> {
        return apiCall { api.getSimilarTvSeries(id) }
    }

    override suspend fun getReviews(id: Int, page: Int): Resources<ResponseDto<List<Review>>> {
        return apiCall { api.getTvSeriesReviews(id,page) }
    }
}