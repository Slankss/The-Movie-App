package com.okankkl.themovieapp.data.repository

import com.okankkl.themovieapp.utils.Resources
import com.okankkl.themovieapp.data.datasource.content.ContentDataSource
import com.okankkl.themovieapp.data.response.ResponseDto
import com.okankkl.themovieapp.domain.model.Content
import com.okankkl.themovieapp.domain.repository.MultiContentRepository
import javax.inject.Inject

class MultiContentRepositoryImpl @Inject constructor(
    private val dataSource: ContentDataSource
) : MultiContentRepository {
    override suspend fun search(query: String, page: Int): Resources<ResponseDto<List<Content>>> {
        return dataSource.search(query, page)
    }
}