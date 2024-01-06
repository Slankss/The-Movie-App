package com.okankkl.themovieapp.enum_sealed

enum class Categories(
    var title : String,
    var path : String
)
{
    Trending(
        path = "trending",
        title = "Trending"
    ),
    Popular(
        path = "popular",
        title =  "Popular"
    ),
    TopRated(
        path = "top_rated",
        title = "Top Rated"
    ),
    NowPlaying(
        path = "now_playing",
        title = "Now Playing"
    ),
    OnTheAir(
        path = "on_the_air",
        title = "On The Air"
    )
}

sealed class DataType{
    class Movie(val name : String = "movie") : DataType()
    class TvSeries(val name : String = "tv_series") : DataType()
}

