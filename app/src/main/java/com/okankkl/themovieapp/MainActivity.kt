@file:OptIn(ExperimentalMaterial3Api::class)

package com.okankkl.themovieapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.okankkl.themovieapp.enum_sealed.Pages
import com.okankkl.themovieapp.ui.theme.BackgroundColor
import com.okankkl.themovieapp.ui.theme.TheMovieAppTheme
import com.okankkl.themovieapp.view.Favourites
import com.okankkl.themovieapp.view.MovieDetail
import com.okankkl.themovieapp.view.Home
import dagger.hilt.android.AndroidEntryPoint

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
                    AppActivity()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppActivity()
{
   val scope = rememberCoroutineScope()
   var navController = rememberNavController()


    Scaffold(
        modifier = Modifier,
        containerColor = BackgroundColor
    ) {
        NavHost(navController = navController, startDestination = Pages.Home.route){

            composable(route = Pages.Home.route){
                Home(navController = navController)
            }

            composable(
                route = "${Pages.MovieDetail.route}/{movieId}",
                arguments = listOf(navArgument("movieId"){
                    type = NavType.IntType
                })
                )
            { backStackEntry ->
                MovieDetail(navController = navController,backStackEntry.arguments?.getInt("movieId"))
            }

            composable(route = Pages.Favourites.route){
                Favourites(navController = navController)
            }

        }
    }

}

