package com.okankkl.themovieapp.paging.use_case

import androidx.paging.PagingData
import com.okankkl.themovieapp.enum_sealed.Categories
import com.okankkl.themovieapp.model.TvSeries
import com.okankkl.themovieapp.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTvSeriesUseCase
    @Inject
    constructor(private val repository : Repository) : BaseUseCase<Unit, Flow<PagingData<TvSeries>>>
{
    override suspend fun execute(
        category: Categories,
        input: Unit
    ): Flow<PagingData<TvSeries>>
    {
        return repository.getTvSeriesPage(category)
    }
}