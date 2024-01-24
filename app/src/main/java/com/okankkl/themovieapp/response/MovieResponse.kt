package com.okankkl.themovieapp.response

import com.google.gson.annotations.SerializedName
import com.okankkl.themovieapp.model.Display
import com.okankkl.themovieapp.model.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class MovieResponse(
    var page : Int,
    var results : List<Movie>,
    @SerializedName("total_pages")
    var totalPages : Int,
    @SerializedName("total_results")
    var totalResults : Int
)