package com.okankkl.themovieapp.domain.repository

import com.okankkl.themovieapp.utils.Resources
import com.okankkl.themovieapp.data.response.ResponseDto
import com.okankkl.themovieapp.domain.model.Content

interface MultiContentRepository {
    suspend fun search(query: String, page: Int) : Resources<ResponseDto<List<Content>>>
}