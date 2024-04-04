package com.okankkl.themovieapp.presentation.home_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.okankkl.themovieapp.presentation.components.LoadingUi
import com.okankkl.themovieapp.presentation.Categories
import com.okankkl.themovieapp.presentation.DisplayType
import com.okankkl.themovieapp.presentation.components.ErrorUi
import com.okankkl.themovieapp.presentation.home_screen.components.ContentHorizontalList
import com.okankkl.themovieapp.presentation.home_screen.components.TopMenu
import com.okankkl.themovieapp.presentation.home_screen.components.TrendContentList

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun Home(navController: NavController, contentHomeViewModel : HomeViewModel = hiltViewModel()) {

    val selectedPage = contentHomeViewModel.selectedPage.collectAsState()

    val scrollState = rememberScrollState()

    val state = contentHomeViewModel.state.value

    LaunchedEffect(key1 = true) {
        if (selectedPage.value == DisplayType.Movie)
            contentHomeViewModel.getMovies()
        else
            contentHomeViewModel.getTvSeries()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopMenu(
                modifier = Modifier
                    .padding(top = 30.dp, start = 25.dp, end = 25.dp),
                navController = navController,
                selectedPage = selectedPage
                ) { page ->
                contentHomeViewModel.setSelectedPage(page)
            }
            if(state.data != null){
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    TrendContentList(
                        contents = state.data!!.trending,
                        displayType = selectedPage.value.path,
                        navController = navController,
                        topPadding = 25.dp
                    )

                    ContentHorizontalList(
                        contents = state.data!!.popular,
                        displayType = selectedPage.value.path,
                        moviesType = Categories.Popular,
                        navController = navController
                    )
                    ContentHorizontalList(
                        contents = state.data!!.now_playing,
                        displayType = selectedPage.value.path,
                        moviesType = if(selectedPage.value == DisplayType.Movie) Categories.NowPlaying
                        else Categories.OnTheAir,
                        navController = navController
                    )
                    ContentHorizontalList(
                        contents = state.data!!.top_rated,
                        displayType = selectedPage.value.path,
                        moviesType = Categories.TopRated,
                        navController = navController
                    )
                }
            }
        }

        if (state.is_loading) LoadingUi(
            modifier = Modifier.align(Alignment.Center)
        )
        if(state.message.isNotBlank()){
            ErrorUi(errorMsg = state.message)
        }

    }
}



