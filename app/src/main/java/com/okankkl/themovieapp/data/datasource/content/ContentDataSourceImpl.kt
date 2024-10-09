package com.okankkl.themovieapp.data.datasource.content

import com.okankkl.themovieapp.utils.Resources
import com.okankkl.themovieapp.utils.apiCall
import com.okankkl.themovieapp.data.remote.TmdbApi
import com.okankkl.themovieapp.data.response.ResponseDto
import com.okankkl.themovieapp.domain.model.Content
import javax.inject.Inject

class ContentDataSourceImpl @Inject constructor(
    private val api: TmdbApi
) : ContentDataSource {
    override suspend fun search(query: String, page: Int): Resources<ResponseDto<List<Content>>> {
        return apiCall { api.getSearch(query = query, page = page) }
    }
}