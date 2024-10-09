package com.okankkl.themovieapp.data.datasource.tvseries.local

import com.okankkl.themovieapp.utils.Resources
import com.okankkl.themovieapp.utils.roomCall
import com.okankkl.themovieapp.data.local.database.LocalDb
import com.okankkl.themovieapp.data.model.entity.TvSeriesEntity
import com.okankkl.themovieapp.data.mapper.toTvSeriesEntity
import com.okankkl.themovieapp.data.model.dto.TvSeries
import javax.inject.Inject

class TvSeriesLocalDataSourceImpl @Inject constructor(
    private val db: LocalDb
) : TvSeriesLocalDataSource {
    override suspend fun getTvSeries(): Resources<List<TvSeriesEntity>> {
        return roomCall { db.getTvSeries() }
    }

    override suspend fun addTvSeries(tvSeries: List<TvSeries>) {
        db.addTvSeries(
            tvSeries.map { it.toTvSeriesEntity() }
        )
    }

    override suspend fun clearTvSeries() {
        db.deleteTvSeries()
    }
}