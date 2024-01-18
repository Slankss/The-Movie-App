@file:OptIn(ExperimentalMaterial3Api::class)

package com.okankkl.themovieapp

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.service.voice.VoiceInteractionSession.ActivityId
import android.util.Log

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.okankkl.themovieapp.enum_sealed.Pages
import com.okankkl.themovieapp.model.StoreData
import com.okankkl.themovieapp.ui.theme.BackgroundColor
import com.okankkl.themovieapp.ui.theme.TheMovieAppTheme
import com.okankkl.themovieapp.view.Favourites
import com.okankkl.themovieapp.view.MovieDetail
import com.okankkl.themovieapp.view.Home
import com.okankkl.themovieapp.view.TvSeriesDetail
import com.okankkl.themovieapp.view.ViewAll
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.okankkl.themovieapp.components.BottomMenuItem
import com.okankkl.themovieapp.enum_sealed.MenuItems
import com.okankkl.themovieapp.extensions.isNetworkAvailable
import com.okankkl.themovieapp.ui.theme.BottomMenuColor
import com.okankkl.themovieapp.ui.theme.ShadowColor
import com.okankkl.themovieapp.ui.theme.ShadowColor2
import com.okankkl.themovieapp.view.News
import com.okankkl.themovieapp.view.SplashScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {



        super.onCreate(savedInstanceState)
        setContent {
            TheMovieAppTheme {
                // A surface container using the 'background' color from the theme
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
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun AppActivity()
{
    val scope = rememberCoroutineScope()
    var navController = rememberNavController()
    var menuVisibility by remember{
        mutableStateOf(false)
    }

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
        bottomBar = {
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
                    SplashScreen()

                    navController.navigate(Pages.Home.route){
                        popUpTo(Pages.Splash.route) {
                            inclusive = true
                        }
                    }
                }
                composable(
                    route = Pages.Home.route,
                    enterTransition = {
                        fadeIn(
                            animationSpec = tween(
                                500, easing = LinearEasing
                            )
                        ) + slideIntoContainer(
                            animationSpec = tween(500, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.End
                        )
                    },
                    exitTransition = {
                        fadeOut(
                            animationSpec = tween(
                                500, easing = LinearEasing
                            )
                        ) + slideOutOfContainer(
                            animationSpec = tween(500, easing = EaseOut),
                            towards = AnimatedContentTransitionScope.SlideDirection.Start
                        )
                    }

                ){
                    Home(navController = navController)
                }
                composable(
                    route = "${Pages.MovieDetail.route}/{movieId}",
                    arguments = listOf(navArgument("movieId"){
                        type = NavType.IntType
                    }),
                    enterTransition = {
                        fadeIn(
                            animationSpec = tween(
                                500, easing = LinearEasing
                            )
                        ) + slideIntoContainer(
                            animationSpec = tween(500, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.Start
                        )
                    },
                    exitTransition = {
                        fadeOut(
                            animationSpec = tween(
                                500, easing = LinearEasing
                            )
                        ) + slideOutOfContainer(
                            animationSpec = tween(500, easing = EaseOut),
                            towards = AnimatedContentTransitionScope.SlideDirection.End
                        )
                    }
                )
                { backStackEntry ->
                    MovieDetail(navController = navController,backStackEntry.arguments?.getInt("movieId"),)
                }

                composable(
                    route = "${Pages.TvSeriesDetail.route}/{tvSeriesId}",
                    arguments = listOf(navArgument("tvSeriesId"){
                        type = NavType.IntType
                    }),
                    enterTransition = {
                        fadeIn(
                            animationSpec = tween(
                                500, easing = LinearEasing
                            )
                        ) + slideIntoContainer(
                            animationSpec = tween(500, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.Start
                        )
                    },
                    exitTransition = {
                        fadeOut(
                            animationSpec = tween(
                                500, easing = LinearEasing
                            )
                        ) + slideOutOfContainer(
                            animationSpec = tween(500, easing = EaseOut),
                            towards = AnimatedContentTransitionScope.SlideDirection.End
                        )
                    }
                )
                { backStackEntry ->
                    TvSeriesDetail(navController = navController,backStackEntry.arguments?.getInt("tvSeriesId"))
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
                                500, easing = LinearEasing
                            )
                        ) + slideIntoContainer(
                            animationSpec = tween(500, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.Start
                        )
                    },
                    exitTransition = {
                        fadeOut(
                            animationSpec = tween(
                                500, easing = LinearEasing
                            )
                        ) + slideOutOfContainer(
                            animationSpec = tween(500, easing = EaseOut),
                            towards = AnimatedContentTransitionScope.SlideDirection.End
                        )
                    }
                ){ backStackEntry ->
                    val arguments = requireNotNull(backStackEntry.arguments)
                    val dataType = arguments.getString("dataType")
                    val category = arguments.getString("category")
                    ViewAll(navController = navController,dataType = dataType, category = category)
                }

                composable(
                    route = Pages.Favourites.route
                ){
                    Favourites(navController = navController)
                }

                composable(
                    route = Pages.New.route
                ){
                    News(navController = navController)
                }

            }
        }
    }

}







