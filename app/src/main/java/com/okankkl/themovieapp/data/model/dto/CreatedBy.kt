package com.okankkl.themovieapp.data.model.dto

import com.google.gson.annotations.SerializedName

data class CreatedBy(
    @SerializedName("credit_id")
    val creditId: String,
    val gender: Int,
    val id: Int,
    val name: String,
    @SerializedName("profile_path")
    val profilePath: String?
)