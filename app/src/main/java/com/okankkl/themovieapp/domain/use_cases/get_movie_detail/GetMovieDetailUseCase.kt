package com.okankkl.themovieapp.domain.use_cases.get_movie_detail

import com.okankkl.themovieapp.common.Resources
import com.okankkl.themovieapp.domain.model.Movie
import com.okankkl.themovieapp.domain.repository.ApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val apiRepository: ApiRepository
){
    fun getMovieDetail(id : Int) : Flow<Resources<Movie>> = flow {
        try {
            emit(Resources.Loading())
            val movie = apiRepository.getMovieDetail(id)
            emit(Resources.Success(data = movie))

        } catch (e : Exception){
            emit(Resources.Failed(message = e.localizedMessage ?: "Un exptected error occures"))
        }
    }
}