package com.okankkl.themovieapp.data.model.dto

import com.google.gson.annotations.SerializedName

data class Review(
    val author: String? = null,
    @SerializedName("author_details")
    val authorDetails: Author? = null,
    val content: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    val id: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
    val url: String? = null
)
