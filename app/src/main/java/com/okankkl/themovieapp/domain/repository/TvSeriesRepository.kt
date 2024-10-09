package com.okankkl.themovieapp.domain.repository

import com.okankkl.themovieapp.data.model.dto.Review
import com.okankkl.themovieapp.utils.Categories
import com.okankkl.themovieapp.utils.Resources
import com.okankkl.themovieapp.data.model.dto.TvSeries
import com.okankkl.themovieapp.data.model.entity.TvSeriesEntity
import com.okankkl.themovieapp.data.response.ResponseDto

interface TvSeriesRepository {

    suspend fun getRemoteTvSeries(category: Categories, page: Int): Resources<ResponseDto<List<TvSeries>>>

    suspend fun getLocalTvSeries(): Resources<List<TvSeriesEntity>>

    suspend fun addTvSeriesToLocal(tvSeries: List<TvSeries>)

    suspend fun deleteLocalTvSeries()

    suspend fun getSimilarTvSeries(id: Int): Resources<ResponseDto<List<TvSeries>>>

    suspend fun getTvSeriesDetail(id: Int): Resources<TvSeries>

    suspend fun getReviews(id: Int,page: Int): Resources<ResponseDto<List<Review>>>
}