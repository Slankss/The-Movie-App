package com.okankkl.themovieapp.data.model.dto

import com.google.gson.annotations.SerializedName

data class Author(
    val name: String? = null,
    val username: String? = null,
    @SerializedName("avatar_path")
    val avatarPath: String? = null,
    val rating: Double? = null
)
