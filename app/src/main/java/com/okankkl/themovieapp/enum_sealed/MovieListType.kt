package com.okankkl.themovieapp.enum_sealed

enum class MovieListType(
    title : String
)
{
    Popular(
        title =  "Popular"
    ),
    TopRated(
        title = "Top Rated"
    ),
    NowPlaying(
        title = "Now Playing"
    ),
    Trending(
        title = "Trending"
    )
}