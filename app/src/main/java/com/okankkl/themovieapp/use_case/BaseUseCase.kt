package com.okankkl.themovieapp.use_case

import com.okankkl.themovieapp.enum_sealed.Categories

interface BaseUseCase<In,Out>
{
    suspend fun execute(category : Categories,input : Unit) : Out
}