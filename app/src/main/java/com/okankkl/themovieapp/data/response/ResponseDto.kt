package com.okankkl.themovieapp.data.response

import com.google.gson.annotations.SerializedName

data class ResponseDto<T>(
    val id : Int? = null,
    val page : Int? = null,
    val results : T? = null,
    @SerializedName("total_pages")
    val totalPages : Int? = null,
    @SerializedName("total_results")
    val totalResults : Int? = null
)