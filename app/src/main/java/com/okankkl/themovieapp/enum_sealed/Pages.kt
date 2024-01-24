package com.okankkl.themovieapp.enum_sealed

import com.okankkl.themovieapp.R

enum class Pages(
    var route : String,
    var title : String
)
{
    Home(
        route = "home",
        title = "Home"
    ),
    DisplayDetail(
        route = "display_detail",
        title = "Display Detail"
    ),
    Favourites(
        route = "favourites",
        title = "Favourites"
    ),
    DisplayList(
        route = "displays",
        title = "Displays"
    ),
    ViewAll(
        route = "view_all",
        title = "View All"
    ),
    New(
        route = "new",
        title = "New"
    ),
    Splash(
        route = "splash",
        title = "Splash"
    ),
    Search(
        route = "search",
        title = "Search"
    )
}

enum class MenuItems(
    var route : String,
    var title : String,
    var selectedIcon : Int,
    var unSelectedIcon : Int
){
    Home(
        route = "home",
        title = "Home",
        selectedIcon = R.drawable.ic_home_selected,
        unSelectedIcon = R.drawable.ic_home_unselected
    ),
    New(
        route = "new",
        title = "New",
        selectedIcon = R.drawable.ic_new_selected,
        unSelectedIcon = R.drawable.ic_new_unselected
    ),
    Favourites(
        route = "favourites",
        title = "Favourites",
        selectedIcon = R.drawable.ic_fav_selected,
        unSelectedIcon = R.drawable.ic_fav_unselected
    )

}