package com.okankkl.themovieapp.domain.use_cases.get_tv_series_detail

import com.okankkl.themovieapp.common.Resources
import com.okankkl.themovieapp.domain.model.TvSeries
import com.okankkl.themovieapp.domain.repository.ApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTvSeriesDetailUseCase @Inject constructor(
    private val apiRepository: ApiRepository
){
    fun getTvSeriesDetail(id : Int) : Flow<Resources<TvSeries>> = flow {
        try {
            emit(Resources.Loading())
            val tvSeries = apiRepository.getTvSeriesDetail(id)
            emit(Resources.Success(data = tvSeries))

        } catch (e : Exception){
            emit(Resources.Failed(message = e.localizedMessage ?: "Un exptected error occures"))
        }
    }
}