package com.okankkl.themovieapp.domain.model.response

class ResponseDto<T : Any?>
{
    val results : T? = null
    val page : Int? = null
    val totalPages : Int? = null
    val totalResults : Int? = null
}