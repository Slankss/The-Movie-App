package com.okankkl.themovieapp.domain.use_cases.search_content
import com.okankkl.themovieapp.common.Resources
import com.okankkl.themovieapp.data.remote.dto.toMultiSearchContent
import com.okankkl.themovieapp.domain.model.MultiSearchContent
import com.okankkl.themovieapp.domain.repository.ApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchContentUseCase @Inject constructor(
    private val apiRepository: ApiRepository
){
    fun searchContent(query : String) : Flow<Resources<List<MultiSearchContent>>> = flow {
        try {
            emit(Resources.Loading())
            val contents = apiRepository.search(query).results.map { it.toMultiSearchContent() }
            if(contents.isEmpty())
                emit(Resources.Failed(message = "No contents"))
            else
                emit(Resources.Success(data = contents))
        } catch (e : Exception){
            emit(Resources.Failed(message = e.localizedMessage ?: "Unexpected error occured."))
        }
    }

}