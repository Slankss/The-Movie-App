package com.okankkl.themovieapp.domain.model

import com.google.gson.annotations.SerializedName

open class Content(
    val id: Int? = null,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    val title: String? = null,
    val category: String? = null,
    @SerializedName("media_type")
    open val contentType: String? = null,
    val releaseDate: String? = null
)