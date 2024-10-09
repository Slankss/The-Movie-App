package com.okankkl.themovieapp.data.datasource.tvseries.remote

import com.okankkl.themovieapp.data.model.dto.Review
import com.okankkl.themovieapp.utils.Categories
import com.okankkl.themovieapp.utils.Resources
import com.okankkl.themovieapp.data.model.dto.TvSeries
import com.okankkl.themovieapp.data.response.ResponseDto

interface TvSeriesRemoteDataSource {

    suspend fun getTvSeries(category: Categories, page: Int): Resources<ResponseDto<List<TvSeries>>>

    suspend fun getTrendingTvSeries(): Resources<ResponseDto<List<TvSeries>>>

    suspend fun getTvDetail(id: Int): Resources<TvSeries>

    suspend fun getSimilarTvSeries(id: Int): Resources<ResponseDto<List<TvSeries>>>

    suspend fun getReviews(id: Int, page: Int): Resources<ResponseDto<List<Review>>>
}