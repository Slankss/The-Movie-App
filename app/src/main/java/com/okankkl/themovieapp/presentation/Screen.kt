package com.okankkl.themovieapp.presentation

import android.graphics.drawable.Icon
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NamedNavArgument
import androidx.navigation.navArgument
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.utils.AnimationUtils
import com.okankkl.themovieapp.utils.Constants.CONTENT_DETAIL_ROUTE
import androidx.navigation.NavType
import com.okankkl.themovieapp.utils.Constants.ARG_CATEGORY
import com.okankkl.themovieapp.utils.Constants.ARG_CONTENT_DETAIL_ID
import com.okankkl.themovieapp.utils.Constants.ARG_CONTENT_TYPE
import com.okankkl.themovieapp.utils.Constants.FAVOURITES_ROUTE
import com.okankkl.themovieapp.utils.Constants.HOME_ROUTE
import com.okankkl.themovieapp.utils.Constants.NEW_MOVIES_ROUTE
import com.okankkl.themovieapp.utils.Constants.SEARCH_ROUTE
import com.okankkl.themovieapp.utils.Constants.SPLASH_ROUTE
import com.okankkl.themovieapp.utils.Constants.VIEW_ALL_ROUTE

enum class Screen(
    val route : String,
    val title : String,
    val args: List<NamedNavArgument> = emptyList(),
    val enterAnimation: EnterTransition,
    val exitAnimation: ExitTransition
)
{
    Home(
        route = HOME_ROUTE,
        title = "Home",
        enterAnimation = EnterTransition.None,
        exitAnimation = ExitTransition.None
    ),
    ContentDetail(
        route = CONTENT_DETAIL_ROUTE,
        title = "Display Detail",
        enterAnimation = AnimationUtils.enterAnimLeftToRight(),
        exitAnimation = AnimationUtils.exitAnimRightToLeft(),
        args = listOf(
            navArgument(ARG_CONTENT_DETAIL_ID) { type = NavType.IntType },
            navArgument(ARG_CONTENT_TYPE) { type = NavType.StringType }
        )
    ),
    Favourites(
        route = FAVOURITES_ROUTE,
        title = "Favourites",
        enterAnimation = EnterTransition.None,
        exitAnimation = ExitTransition.None
    ),
    ViewAll(
        route = VIEW_ALL_ROUTE,
        title = "View All",
        enterAnimation = AnimationUtils.enterAnimRightToLeft(),
        exitAnimation = AnimationUtils.exitAnimLeftToRight(),
        args = listOf(
            navArgument(ARG_CONTENT_TYPE) { type = NavType.StringType },
            navArgument(ARG_CATEGORY) { type = NavType.StringType }
        )
    ),
    NewMovies(
        route = NEW_MOVIES_ROUTE,
        title = "New",
        enterAnimation = EnterTransition.None,
        exitAnimation = ExitTransition.None
    ),
    Splash(
        route = SPLASH_ROUTE,
        title = "Splash",
        enterAnimation = AnimationUtils.enterAnimLeftToRight(),
        exitAnimation = AnimationUtils.exitAnimRightToLeft()
    ),
    Search(
        route = SEARCH_ROUTE,
        title = "Search",
        enterAnimation = AnimationUtils.enterAnimLeftToRight(),
        exitAnimation = AnimationUtils.exitAnimRightToLeft()
    )
}

enum class MenuItem(
    val screen: Screen,
    var selectedIcon : Int,
    var unSelectedIcon : Int
){
    Home(
        screen = Screen.Home,
        selectedIcon = R.drawable.ic_home_selected,
        unSelectedIcon = R.drawable.ic_home_unselected
    ),
    NewMovies(
        screen = Screen.NewMovies,
        selectedIcon = R.drawable.ic_new_selected,
        unSelectedIcon = R.drawable.ic_new_unselected
    ),
    Favourites(
        screen = Screen.Favourites,
        selectedIcon = R.drawable.ic_fav_selected,
        unSelectedIcon = R.drawable.ic_fav_unselected
    )
}

enum class ContentDetailMenuItem(
    val title: String,
    val icon: Int
){
    Detail(
        title = "Detail",
        icon = R.drawable.ic_share
    ),
    Trailers(
        title = "Trailers",
        icon = R.drawable.ic_search
    ),
    Reviews(
        title = "Reviews",
        icon = R.drawable.ic_share
    )
}