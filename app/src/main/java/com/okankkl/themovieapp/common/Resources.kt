package com.okankkl.themovieapp.common

sealed class Resources<T>(var data : T? = null,var message : String = "")
{
    class Success<T>(data : T?) : Resources<T>(data = data)
    class Loading<T> : Resources<T>()
    class Failed<T>(message : String) : Resources<T>(message = message)
}