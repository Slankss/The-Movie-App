package com.okankkl.themovieapp.data.repository

import com.okankkl.themovieapp.data.datasource.tvseries.local.TvSeriesLocalDataSource
import com.okankkl.themovieapp.data.datasource.tvseries.remote.TvSeriesRemoteDataSource
import com.okankkl.themovieapp.data.model.dto.Review
import com.okankkl.themovieapp.domain.repository.TvSeriesRepository
import com.okankkl.themovieapp.utils.Categories
import com.okankkl.themovieapp.utils.Resources
import com.okankkl.themovieapp.data.model.dto.TvSeries
import com.okankkl.themovieapp.data.model.entity.TvSeriesEntity
import com.okankkl.themovieapp.data.response.ResponseDto
import javax.inject.Inject

class TvSeriesRepositoryImpl @Inject constructor(
    private val localDataSource: TvSeriesLocalDataSource,
    private val remoteDataSource: TvSeriesRemoteDataSource
) : TvSeriesRepository {

    override suspend fun getRemoteTvSeries(
        category: Categories,
        page: Int
    ): Resources<ResponseDto<List<TvSeries>>> {
        return if (category == Categories.Trending){
            remoteDataSource.getTrendingTvSeries()
        } else {
            remoteDataSource.getTvSeries(category, page)
        }
    }

    override suspend fun getLocalTvSeries(): Resources<List<TvSeriesEntity>> {
        return localDataSource.getTvSeries()
    }

    override suspend fun addTvSeriesToLocal(tvSeries: List<TvSeries>) {
        localDataSource.addTvSeries(tvSeries)
    }

    override suspend fun deleteLocalTvSeries() {
        localDataSource.clearTvSeries()
    }

    override suspend fun getSimilarTvSeries(id: Int): Resources<ResponseDto<List<TvSeries>>> {
        return remoteDataSource.getSimilarTvSeries(id)
    }

    override suspend fun getTvSeriesDetail(id: Int): Resources<TvSeries> {
        return remoteDataSource.getTvDetail(id)
    }

    override suspend fun getReviews(id: Int, page: Int): Resources<ResponseDto<List<Review>>> {
        return remoteDataSource.getReviews(id, page)
    }
}