package com.okankkl.themovieapp.repository

import androidx.paging.PagingData
import com.okankkl.themovieapp.enum_sealed.Categories
import com.okankkl.themovieapp.enum_sealed.DataType
import com.okankkl.themovieapp.enum_sealed.Resources
import kotlinx.coroutines.flow.Flow


interface Repository
{
    suspend fun getDisplayList(type : DataType,moviesType: Categories,page : Int) : Resources
    suspend fun getDisplayDetail(type : DataType,id : Int) : Resources
    suspend fun getSimilarDisplayList(type: DataType,id : Int) : Resources
    suspend fun getPages(category : Categories) : Flow<PagingData<Any>>


}