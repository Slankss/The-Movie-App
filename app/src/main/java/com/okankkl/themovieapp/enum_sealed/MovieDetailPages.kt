package com.okankkl.themovieapp.enum_sealed

enum class MovieDetailPages(
    val pageName : String
)
{
    Overview(
        pageName = "Overview"
    ),
    Detail(
        pageName = "Details"
    ),
    Trailer(
        pageName = "Trailer"
    )

}