package com.okankkl.themovieapp.data.datasource.person

import com.okankkl.themovieapp.data.model.dto.Person
import com.okankkl.themovieapp.data.remote.TmdbApi
import com.okankkl.themovieapp.utils.Resources
import com.okankkl.themovieapp.utils.apiCall

class PersonDataSourceImpl(
    private val api: TmdbApi
): PersonDataSource {
    override suspend fun getPersonDetail(id: Int): Resources<Person> {
        return apiCall { api.getPersonDetail(id) }
    }
}