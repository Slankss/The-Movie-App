package com.okankkl.themovieapp.data.paging.use_case

import androidx.paging.PagingData
import com.okankkl.themovieapp.data.remote.dto.TvSeriesDto
import com.okankkl.themovieapp.presentation.Categories
import com.okankkl.themovieapp.domain.model.TvSeries
import com.okankkl.themovieapp.domain.repository.ApiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTvSeriesUseCase
    @Inject
    constructor(private val apiRepository : ApiRepository) :
    BaseUseCase<Unit, Flow<PagingData<TvSeriesDto>>>
{
    override suspend fun execute(
        category: Categories,
        input: Unit
    ): Flow<PagingData<TvSeriesDto>>
    {
        return apiRepository.getTvSeriesPage(category)
    }
}