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
        route = "home",
        title = "Movies"
    ),
    TvSeriesList(
        route = "home",
        title = "Tv Series"
    )
}