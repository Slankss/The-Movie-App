package com.okankkl.themovieapp.domain.use_cases.get_similar_movies

import com.okankkl.themovieapp.common.Resources
import com.okankkl.themovieapp.domain.model.Content
import com.okankkl.themovieapp.domain.repository.ApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSimilarMoviesUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    fun getSimilarMovies(id : Int) : Flow<Resources<List<Content>>> = flow {

        try {
            emit(Resources.Loading())
            val similarMovies = apiRepository.getSimilarMovies(id).results
            emit(Resources.Success(data = similarMovies))
        } catch (e : Exception){
            emit(Resources.Failed(message = e.localizedMessage ?: "Unexpected error occured"))
        }

    }

}