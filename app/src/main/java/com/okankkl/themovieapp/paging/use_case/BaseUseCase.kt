package com.okankkl.themovieapp.paging.use_case

import com.okankkl.themovieapp.enum_sealed.Categories
import com.okankkl.themovieapp.enum_sealed.DataType

interface BaseUseCase<In,Out>
{
    suspend fun execute(category : Categories,input : Unit) : Out
}