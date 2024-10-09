package com.okankkl.themovieapp.data.repository

import com.okankkl.themovieapp.data.datasource.person.PersonDataSource
import com.okankkl.themovieapp.data.model.dto.Person
import com.okankkl.themovieapp.domain.repository.PersonRepository
import com.okankkl.themovieapp.utils.Resources

class PersonRepositoryImpl(
    private val dataSource: PersonDataSource
) : PersonRepository {
    override suspend fun getPersonDetail(id: Int): Resources<Person> {
        return dataSource.getPersonDetail(id)
    }
}