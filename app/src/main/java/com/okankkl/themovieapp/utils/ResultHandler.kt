package com.okankkl.themovieapp.utils

import retrofit2.Response

suspend fun <T> roomCall(request: suspend () -> T) : Resources<T>{
    return try {
        Resources.Success(request())
    } catch (e: Exception){
        Resources.Failed(e.localizedMessage ?: "Unexpected Error!")
    }
}

suspend fun <T> apiCall(request: suspend () -> Response<T>) : Resources<T>{
    return try {
        val response = request()
        if(response.isSuccessful){
            Resources.Success(response.body())
        } else {
            Resources.Failed(response.message())
        }
    } catch (e: Exception){
        Resources.Failed(e.localizedMessage ?: "Unexpected Error!")
    }
}