package com.okankkl.themovieapp.data.paging.use_case

import androidx.paging.PagingData
import com.okankkl.themovieapp.data.remote.dto.MovieDto
import com.okankkl.themovieapp.presentation.Categories
import com.okankkl.themovieapp.domain.model.Movie
import com.okankkl.themovieapp.domain.repository.ApiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase
    @Inject
    constructor(private val apiRepository : ApiRepository) :
    BaseUseCase<Unit, Flow<PagingData<MovieDto>>>
{
    override suspend fun execute(category: Categories, input: Unit): Flow<PagingData<MovieDto>>
    {
        return apiRepository.getMoviesPage(category)
    }
}