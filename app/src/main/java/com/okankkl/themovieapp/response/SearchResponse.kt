package com.okankkl.themovieapp.response

import com.google.gson.annotations.SerializedName
import com.okankkl.themovieapp.model.Search


class SearchResponse
    (
    var page : Int,
    var results : List<Search>,
    @SerializedName("total_pages")
    var totalPages : Int,
    @SerializedName("total_results")
    var totalResults : Int
    )