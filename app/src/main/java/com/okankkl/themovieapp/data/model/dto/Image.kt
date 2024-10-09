package com.okankkl.themovieapp.data.model.dto

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("aspect_ratio")
    val aspectRatio: Double? = null,
    val height: Int? = null,
    val iso_639_1: String? = null,
    @SerializedName("file_path")
    val filePath: String? = null,
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
    @SerializedName("vote_count")
    val voteCount: Int? = null,
    val width: Int? = null
)
