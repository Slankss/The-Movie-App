package com.okankkl.themovieapp.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.okankkl.themovieapp.SessionViewModel
import com.okankkl.themovieapp.utils.Constants.ARG_CATEGORY
import com.okankkl.themovieapp.utils.Constants.ARG_CONTENT_DETAIL_ID
import com.okankkl.themovieapp.utils.Constants.ARG_CONTENT_TYPE
import com.okankkl.themovieapp.presentation.Screen
import com.okankkl.themovieapp.presentation.screens.content_detail.ContentDetailScreen
import com.okankkl.themovieapp.presentation.screens.favourite.FavouritesScreen
import com.okankkl.themovieapp.presentation.screens.home.HomeScreen
import com.okankkl.themovieapp.presentation.screens.news.NewsMoviesScreen
import com.okankkl.themovieapp.presentation.screens.search.SearchPage
import com.okankkl.themovieapp.presentation.screens.splash.SplashScreen
import com.okankkl.themovieapp.presentation.screens.view_all.ViewAllScreen
import kotlinx.coroutines.launch

@Composable
fun BaseNavigation(
    navController: NavHostController,
    showMessage: (String) -> Unit,
    sessionViewModel: SessionViewModel
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        Screen.Splash.apply {
            composable(
                route = route,
                enterTransition = { enterAnimation },
                exitTransition = { exitAnimation },
            ) {
                SplashScreen {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }

        Screen.Home.apply {
            composable(
                route = route,
                enterTransition = { enterAnimation },
                exitTransition = { exitAnimation }
            ) {
                HomeScreen(
                    navigateToContentDetail = { contentId, contentType ->
                        navController.navigate("content_detail/$contentId&$contentType")
                    },
                    navigateToViewAll = { contentType, category ->
                        navController.navigate("view_all/$contentType&$category")
                    },
                    navigateToSearch = {
                        navController.navigate(Screen.Search.route)
                    },
                    sessionViewModel = sessionViewModel
                )
            }
        }

        Screen.ContentDetail.apply {
            composable(
                route = route,
                arguments = args,
                enterTransition = { enterAnimation },
                exitTransition = { exitAnimation }
            ) { backStackEntry ->
                ContentDetailScreen(
                    contentId = backStackEntry.arguments?.getInt(ARG_CONTENT_DETAIL_ID),
                    contentType = backStackEntry.arguments?.getString(ARG_CONTENT_TYPE),
                    sessionViewModel = sessionViewModel,
                    navigateToContentDetail = { contentId, contentType ->
                        navController.navigate("content_detail/$contentId&$contentType")
                    }
                )
            }
        }

        Screen.ViewAll.apply {
            composable(
                route = route,
                arguments = args,
                enterTransition = { enterAnimation },
                exitTransition = { exitAnimation }
            ) { backStackEntry ->
                ViewAllScreen(
                    contentType = backStackEntry.arguments?.getString(ARG_CONTENT_TYPE),
                    category = backStackEntry.arguments?.getString(ARG_CATEGORY),
                    navigateToContentDetail = { contentId, contentType ->
                        navController.navigate("content_detail/$contentId&$contentType")
                    },
                    sessionViewModel = sessionViewModel
                )
            }
        }

        Screen.Search.apply {
            composable(
                route = route,
                enterTransition = { enterAnimation },
                exitTransition = { exitAnimation }
            ) {
                SearchPage(
                    navigateToContentDetail = { contentId, contentType ->
                        navController.navigate("content_detail/$contentId&$contentType")
                    },
                    onMessage = { message ->
                        showMessage(message)
                    },
                    sessionViewModel = sessionViewModel
                )
            }
        }

        Screen.Favourites.apply {
            composable(
                route = route,
                enterTransition = { enterAnimation },
                exitTransition = { exitAnimation }
            ) {
                FavouritesScreen(
                    navigateToContentDetail = { contentId, contentType ->
                        navController.navigate("content_detail/$contentId&$contentType")
                    },
                    sessionViewModel = sessionViewModel
                )
            }
        }

        Screen.NewMovies.apply {
            composable(
                route = route,
                enterTransition = { enterAnimation },
                exitTransition = { exitAnimation }
            ) {
                NewsMoviesScreen(
                    navigateToContentDetail = { contentId, contentType ->
                        navController.navigate("content_detail/$contentId&$contentType")
                    }
                )
            }
        }
    }
}