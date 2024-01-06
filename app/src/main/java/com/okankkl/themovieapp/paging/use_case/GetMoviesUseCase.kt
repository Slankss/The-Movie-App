package com.okankkl.themovieapp.paging.use_case

import androidx.paging.PagingData
import com.okankkl.themovieapp.enum_sealed.Categories
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase
    @Inject
    constructor(private val repository : Repository) : BaseUseCase<Unit, Flow<PagingData<Any>>>
{
    override suspend fun execute(categories: Categories,input: Unit): Flow<PagingData<Any>>
    {
        return repository.getPages(categories)
    }
}