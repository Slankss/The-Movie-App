package com.okankkl.themovieapp.utils

enum class Categories(
    var title: String,
    var path: String
) {
    Trending(
        path = "trending",
        title = "Trending"
    ),
    Popular(
        path = "popular",
        title = "Popular"
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
    ),
    UpComing(
        path = "upcoming",
        title = "Upcoming Movies"
    )
}

val moviesCategoryList = listOf(Categories.Popular,Categories.TopRated,Categories.NowPlaying,Categories.Trending)
val tvSeriesCategoryList = listOf(Categories.Popular,Categories.TopRated,Categories.OnTheAir,Categories.Trending)