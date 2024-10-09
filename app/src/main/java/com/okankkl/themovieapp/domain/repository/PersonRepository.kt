package com.okankkl.themovieapp.domain.repository

import com.okankkl.themovieapp.data.model.dto.Person
import com.okankkl.themovieapp.utils.Resources

interface PersonRepository {
    suspend fun getPersonDetail(id:Int) : Resources<Person>
}