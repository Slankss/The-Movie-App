package com.okankkl.themovieapp.response

import com.google.gson.annotations.SerializedName

class ResponseDto<T : Any?>
{
    val results : T? = null
    val page : Int? = null
    val totalPages : Int? = null
    val totalResults : Int? = null
}