package com.okankkl.themovieapp.data.datasource.person

import com.okankkl.themovieapp.data.model.dto.Person
import com.okankkl.themovieapp.utils.Resources

interface PersonDataSource {
    suspend fun getPersonDetail(int: Int) : Resources<Person>
}