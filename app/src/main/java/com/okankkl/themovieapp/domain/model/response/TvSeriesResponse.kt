package com.okankkl.themovieapp.domain.model.response

import com.google.gson.annotations.SerializedName
import com.okankkl.themovieapp.domain.model.TvSeries

data class TvSeriesResponse(
    var page : Int,
    var results : List<TvSeries>,
    @SerializedName("total_pages")
    var totalPages : Int,
    @SerializedName("total_results")
    var totalResults : Int
)