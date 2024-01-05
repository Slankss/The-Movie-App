package com.okankkl.themovieapp.enum_sealed

enum class Pages(
    var route : String,
    var title : String,

)
{
    Home(
        route = "home",
        title = "Home"
    ),
    MovieDetail(
        route = "movie_detail",
        title = "Movie Detail"
    ),
    Favourites(
        route = "favourites",
        title = "Favourites"
    ),
    MovieList(
        route = "home_movies",
        title = "Movies"
    ),
    TvSeriesList(
        route = "home_tv_series",
        title = "Tv Series"
    ),
    TvSeriesDetail(
        route = "tv_series",
        title = "Tv Series"
    ),
    ViewAll(
        route = "view_all",
        title = "View All"
    )
}