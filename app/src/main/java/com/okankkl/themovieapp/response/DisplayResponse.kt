package com.okankkl.themovieapp.response

import com.google.gson.annotations.SerializedName
import com.okankkl.themovieapp.model.Display


class DisplayResponse
    (
    var page : Int,
    var results : List<Display>,
    @SerializedName("total_pages")
    var totalPages : Int,
    @SerializedName("total_results")
    var totalResults : Int
    )