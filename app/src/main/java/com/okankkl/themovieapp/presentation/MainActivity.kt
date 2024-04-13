@file:OptIn(ExperimentalMaterial3Api::class)

package com.okankkl.themovieapp.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.presentation.components.BottomMenuItem
import com.okankkl.themovieapp.domain.extensions.isNetworkAvailable
import com.okankkl.themovieapp.data.local.preferences.StoreData
import com.okankkl.themovieapp.ui.theme.BackgroundColor
import com.okankkl.themovieapp.ui.theme.ShadowColor
import com.okankkl.themovieapp.ui.theme.ShadowColor2
import com.okankkl.themovieapp.ui.theme.TheMovieAppTheme
import com.okankkl.themovieapp.presentation.content_detail_screen.DisplayDetail
import com.okankkl.themovieapp.presentation.favourite_screen.Favourites
import com.okankkl.themovieapp.presentation.home_screen.Home
import com.okankkl.themovieapp.presentation.news_screen.News
import com.okankkl.themovieapp.presentation.search_screen.SearchPage
import com.okankkl.themovieapp.presentation.splash_screen.SplashScreen
import com.okankkl.themovieapp.presentation.view_all_page.ViewAll
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if(isNetworkAvailable()){
                        AppActivity()
                    }
                    else{
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(BackgroundColor)
                        ){
                            Column(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(horizontal = 25.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_alert),
                                    contentDescription = "alert",
                                    modifier = Modifier
                                        .size(64.dp)
                                        .padding(bottom = 15.dp)
                                )

                                Text(
                                    text = "Please check your internet connection",
                                    style = MaterialTheme.typography.headlineLarge.copy(
                                        fontSize = 20.sp,
                                        color = Color.White
                                    ),
                                    textAlign = TextAlign.Center
                                )
                            }

                        }
                    }

                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation", "RestrictedApi")
@Composable
fun AppActivity()
{
    val scope = rememberCoroutineScope()
    val durationMillis = 200
    val navController = rememberNavController()
    val snackbarHost = remember { SnackbarHostState()}
    var menuVisibility by remember{
        mutableStateOf(false)
    }
    val currentDestination = navController.currentBackStackEntryFlow.collectAsState(initial = navController.currentBackStackEntry)
    val backStackEntry by navController.currentBackStackEntryAsState()

    val storeDate = StoreData(LocalContext.current)
    LaunchedEffect(key1 =true, block = {
        runBlocking {
            storeDate.saveMovieUpdateTime("")
            storeDate.saveTvSeriesUpdateTime("")
        }
    })

    Scaffold(
        modifier = Modifier,
        containerColor = BackgroundColor,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHost)
        },
        bottomBar = {
            if(currentDestination.value != null && currentDestination.value!!.destination.route != Pages.Splash.route){
                BottomNavigation(
                    backgroundColor = BackgroundColor,
                    modifier = Modifier
                        .shadow(
                            elevation = 12.dp,
                            ambientColor = ShadowColor,
                            spotColor = ShadowColor2,
                        )
                ){
                    val navBackStactEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStactEntry?.destination
                    MenuItems.values().forEach { menuItem ->
                        val selected = currentDestination?.hierarchy?.any{ it.route == menuItem.route } == true
                        BottomNavigationItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(menuItem.route)
                            },
                            icon = {
                                BottomMenuItem(selected = selected, menuItem = menuItem)
                            },

                            )
                    }
                }
            }
        }
    ) { innerPadding ->

        Surface(
            modifier = Modifier
                .padding(innerPadding),
            color = BackgroundColor
        ){

            NavHost(
                navController = navController, startDestination = Pages.Splash.route,
                enterTransition = { EnterTransition.None},
                exitTransition = { ExitTransition.None}
            ){
                composable(
                    route = Pages.Splash.route,
                    enterTransition = {
                        scaleIn() + expandVertically(expandFrom = Alignment.CenterVertically)
                    }
                    ,
                    exitTransition = {
                        scaleOut() + shrinkVertically(shrinkTowards = Alignment.CenterVertically)
                    },

                ){
                    SplashScreen(navController)
                }
                composable(
                    route = Pages.Home.route,
                    enterTransition = {
                        fadeIn(
                            animationSpec = tween(
                                durationMillis, easing = LinearEasing
                            )
                        ) + slideIntoContainer(
                            animationSpec = tween(durationMillis, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.End
                        )
                    },
                    exitTransition = {
                        fadeOut(
                            animationSpec = tween(
                                durationMillis, easing = LinearEasing
                            )
                        ) + slideOutOfContainer(
                            animationSpec = tween(durationMillis, easing = EaseOut),
                            towards = AnimatedContentTransitionScope.SlideDirection.Start
                        )
                    }

                ){
                    Home(navController = navController)
                }
                composable(
                    route = "${Pages.DisplayDetail.route}/{movieId}&{displayType}",
                    arguments = listOf(
                        navArgument("movieId"){ type = NavType.IntType },
                        navArgument("displayType") { type = NavType.StringType}
                    ),
                    enterTransition = {
                        fadeIn(
                            animationSpec = tween(
                                durationMillis, easing = LinearEasing
                            )
                        ) + slideIntoContainer(
                            animationSpec = tween(durationMillis, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.Start
                        )
                    },
                    exitTransition = {
                        fadeOut(
                            animationSpec = tween(
                                durationMillis, easing = LinearEasing
                            )
                        ) + slideOutOfContainer(
                            animationSpec = tween(durationMillis, easing = EaseOut),
                            towards = AnimatedContentTransitionScope.SlideDirection.End
                        )
                    }
                )
                { backStackEntry ->
                    val arguments = backStackEntry.arguments
                    DisplayDetail(navController = navController,arguments?.getInt("movieId"),arguments?.getString("displayType"))
                }
                composable(
                    route = "${Pages.ViewAll.route}/{dataType}&{category}",
                    arguments = listOf(
                        navArgument("dataType"){ type = NavType.StringType},
                        navArgument("category") { type = NavType.StringType}
                    ),
                    enterTransition = {
                        fadeIn(
                            animationSpec = tween(
                                durationMillis, easing = LinearEasing
                            )
                        ) + slideIntoContainer(
                            animationSpec = tween(durationMillis, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.Start
                        )
                    },
                    exitTransition = {
                        fadeOut(
                            animationSpec = tween(
                                durationMillis, easing = LinearEasing
                            )
                        ) + slideOutOfContainer(
                            animationSpec = tween(durationMillis, easing = EaseOut),
                            towards = AnimatedContentTransitionScope.SlideDirection.End
                        )
                    }
                ){ backStackEntry ->
                    val arguments = requireNotNull(backStackEntry.arguments)
                    val dataType = arguments.getString("dataType")
                    val category = arguments.getString("category")
                    ViewAll(navController = navController,displayType = dataType, category = category)
                }

                composable(
                    route = Pages.Search.route,
                    enterTransition = {
                        fadeIn(
                            animationSpec = tween(
                                durationMillis, easing = LinearEasing
                            )
                        ) + slideIntoContainer(
                            animationSpec = tween(durationMillis, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.Start
                        )
                    },
                    exitTransition = {
                        fadeOut(
                            animationSpec = tween(
                                durationMillis, easing = LinearEasing
                            )
                        ) + slideOutOfContainer(
                            animationSpec = tween(durationMillis, easing = EaseOut),
                            towards = AnimatedContentTransitionScope.SlideDirection.End
                        )
                    }
                ){
                    SearchPage(navController = navController){ message ->
                        scope.launch {
                            snackbarHost.showSnackbar(message = message)
                        }
                    }
                }

                composable(
                    route = Pages.Favourites.route,
                    enterTransition = {
                        fadeIn(
                            animationSpec = tween(
                                durationMillis, easing = LinearEasing
                            )
                        ) + slideIntoContainer(
                            animationSpec = tween(durationMillis, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.Start
                        )
                    },
                    exitTransition = {
                        fadeOut(
                            animationSpec = tween(
                                durationMillis, easing = LinearEasing
                            )
                        ) + slideOutOfContainer(
                            animationSpec = tween(durationMillis, easing = EaseOut),
                            towards = AnimatedContentTransitionScope.SlideDirection.End
                        )
                    }
                ){
                    Favourites(navController = navController)
                }

                composable(
                    route = Pages.New.route,
                    enterTransition = {
                        fadeIn(
                            animationSpec = tween(
                                durationMillis, easing = LinearEasing
                            )
                        ) + slideIntoContainer(
                            animationSpec = tween(durationMillis, easing = EaseIn),
                            towards = if((backStackEntry?.destination != null) && (backStackEntry?.destination!!.route == Pages.Home.route))
                                AnimatedContentTransitionScope.SlideDirection.Start else  AnimatedContentTransitionScope.SlideDirection.End
                        )
                    },
                    exitTransition = {
                        fadeOut(
                            animationSpec = tween(
                                durationMillis, easing = LinearEasing
                            )
                        ) + slideOutOfContainer(
                            animationSpec = tween(durationMillis, easing = EaseOut),
                            towards = if(backStackEntry?.destination != null && backStackEntry?.destination!!.route == Pages.Home.route)
                                AnimatedContentTransitionScope.SlideDirection.End else  AnimatedContentTransitionScope.SlideDirection.Start
                        )
                    }
                ){
                    News(navController = navController)
                }

            }
        }
    }

}







