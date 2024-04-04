package com.okankkl.themovieapp.paging.use_case

import com.okankkl.themovieapp.presentation.Categories

interface BaseUseCase<In,Out>
{
    suspend fun execute(category : Categories, input : Unit) : Out
}