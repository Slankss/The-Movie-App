package com.okankkl.themovieapp.utils

object Constants
{
    const val BASE_URL = "https://api.themoviedb.org/3/"
    val API_KEY = "ef04889b073508dc226ff8ebb049bebd"
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    const val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="

    // Prefs
    const val MOVIE_UPDATE_TIME = "MOVIE_UPDATE_TIME"
    const val TV_SERIES_UPDATE_TIME = "TV_SERIES_UPDATE_TIME"

    // Routes
    val HOME_ROUTE = "home"
    val CONTENT_DETAIL_ROUTE = "content_detail/{contentId}&{contentType}"
    val NEW_MOVIES_ROUTE = "new_movies"
    val SPLASH_ROUTE = "splash"
    val VIEW_ALL_ROUTE = "view_all/{contentType}&{category}"
    val FAVOURITES_ROUTE = "favourites"
    val SEARCH_ROUTE = "search"

    // Args
    const val ARG_CONTENT_DETAIL_ID = "contentId"
    const val ARG_CONTENT_TYPE = "contentType"
    const val ARG_CATEGORY = "category"
}