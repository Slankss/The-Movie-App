package com.okankkl.themovieapp.utils

object Constants
{
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "ef04889b073508dc226ff8ebb049bebd"
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    const val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="

    // Prefs
    const val MOVIE_UPDATE_TIME = "MOVIE_UPDATE_TIME"
    const val TV_SERIES_UPDATE_TIME = "TV_SERIES_UPDATE_TIME"

    // Routes
    const val HOME_ROUTE = "home"
    const val CONTENT_DETAIL_ROUTE = "content_detail/{contentId}&{contentType}"
    const val NEW_MOVIES_ROUTE = "new_movies"
    const val SPLASH_ROUTE = "splash"
    const val VIEW_ALL_ROUTE = "view_all/{contentType}&{category}"
    const val FAVOURITES_ROUTE = "favourites"
    const val SEARCH_ROUTE = "search"

    const val YOUTUBE_PLAYER_ROUTE = "full_screen_media/{video_key}"

    // Args
    const val ARG_CONTENT_DETAIL_ID = "contentId"
    const val ARG_CONTENT_TYPE = "contentType"
    const val ARG_CATEGORY = "category"
    const val ARG_VIDEO_KEY = "video_key"
}