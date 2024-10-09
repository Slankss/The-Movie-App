package com.okankkl.themovieapp.utils

enum class ContentType(
    var path: String,
    var title: String
) {
    Movie(
        path = "movie",
        title = "Movies"
    ),
    TvSeries(
        path = "tv",
        title = "TV Series"
    )
}