package com.okankkl.themovieapp.data.datasource.content

import com.okankkl.themovieapp.utils.Resources
import com.okankkl.themovieapp.data.response.ResponseDto
import com.okankkl.themovieapp.domain.model.Content

interface ContentDataSource {
    suspend fun search(query: String, page: Int) : Resources<ResponseDto<List<Content>>>
}