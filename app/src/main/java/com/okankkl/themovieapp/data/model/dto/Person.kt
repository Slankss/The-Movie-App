package com.okankkl.themovieapp.data.model.dto

import com.google.gson.annotations.SerializedName

data class Person(
    val adult: Boolean? = null,
    @SerializedName("also_known_as")
    val alsoKnownAs: List<String>? = null,
    val biography: String? = null,
    val birthday: String? = null,
    @SerializedName("deathday")
    val deathDay: String? = null,
    val gender: Int? = null,
    val homepage: String? = null,
    val id: Int? = null,
    @SerializedName("known_for_department")
    val knownForDepartment: String? = null,
    val name: String? = null,
    @SerializedName("place_of_birth")
    val placeOfBirth: String? = null,
    val popularity: Double? = null,
    @SerializedName("profile_path")
    val profilePath: String? = null
)
