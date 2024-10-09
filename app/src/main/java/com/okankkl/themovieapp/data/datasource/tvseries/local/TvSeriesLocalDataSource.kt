package com.okankkl.themovieapp.data.datasource.tvseries.local

import com.okankkl.themovieapp.utils.Resources
import com.okankkl.themovieapp.data.model.dto.TvSeries
import com.okankkl.themovieapp.data.model.entity.TvSeriesEntity

interface TvSeriesLocalDataSource {

    suspend fun getTvSeries() : Resources<List<TvSeriesEntity>>

    suspend fun addTvSeries(tvSeries: List<TvSeries>)

    suspend fun clearTvSeries()
}