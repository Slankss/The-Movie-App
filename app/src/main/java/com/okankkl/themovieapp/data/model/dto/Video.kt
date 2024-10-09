package com.okankkl.themovieapp.data.model.dto

import com.google.gson.annotations.SerializedName

data class Video(
    val id: String? = null,
    val iso_3166_1: String? = null,
    val iso_639_1: String? = null,
    val key: String? = null,
    val name: String? = null,
    val official: Boolean? = null,
    @SerializedName("published_at")
    val publishedAt: String? = null,
    val site: String? = null,
    val size: Int? = null,
    val type: String? = null
)