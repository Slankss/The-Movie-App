package com.okankkl.themovieapp.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.ui.theme.LightBlue
import androidx.compose.runtime.*
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.okankkl.themovieapp.components.YouTubePlayer
import com.okankkl.themovieapp.enum_sealed.MovieDetailPages
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.enum_sealed.Resources
import com.okankkl.themovieapp.model.Videos
import com.okankkl.themovieapp.util.Util.IMAGE_BASE_URL
import com.okankkl.themovieapp.viewModel.MovieDetailViewModel
import kotlinx.coroutines.launch

@Composable
fun MovieDetail(navController: NavController,movieId : Int?)
{
    var movieViewModel : MovieDetailViewModel = hiltViewModel()
    var movie = movieViewModel.movie.collectAsState()

    SideEffect {
        if(movieId != null){
            movieViewModel.getMovie(movieId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = 30.dp)
    ) {
        when(movie.value){
            is Resources.Loading -> Loading()
            is Resources.Success -> {
                Success(
                    movie = (movie.value as Resources.Success).data as Movie
                )
            }
            is Resources.Failed -> {
                Failed(errorMsg = (movie.value as Resources.Failed).errorMsg)
            }
        }


    }

}

@Composable
fun Success(movie : Movie){
    TopHeader(movie = movie)
    Content(movie = movie)
}

@Composable
fun TopHeader(movie: Movie ){

    var movieRate = movie.voteAverage / 2

    Row(
       modifier = Modifier
           .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){

        Box(
            modifier = Modifier
                .weight(4f)
                .height(200.dp)
                .shadow(
                    elevation = 6.dp,
                    shape = RoundedCornerShape(20.dp)
                )
            ,
            ){
            AsyncImage(
                model = IMAGE_BASE_URL+movie.posterPath,
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
            )
        }

        Column(
            modifier = Modifier
                .weight(6f)
                .padding(start = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                modifier = Modifier,
                text = movie.title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 28.sp,
                    lineHeight = 40.sp
                )
            )

            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ){
                var point = String.format("%.1f",movie.voteAverage)
                Text(
                    modifier = Modifier
                        .padding(end = 5.dp),
                    text = point,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 28.sp,
                        color = LightBlue
                    )
                )

                for(i in 1..5){
                    if( i <= movieRate){
                        Icon(
                            painter = painterResource(id = R.drawable.filled_star),
                            contentDescription = "Filled star",
                            tint = LightBlue,
                            modifier = Modifier
                                .padding(end = 3.dp)

                        )
                    }
                    else if(i - 1 <= movieRate){
                        Icon(
                            painter = painterResource(id = R.drawable.filled_star),
                            contentDescription = "Filled star",
                            modifier = Modifier
                                .padding(end = 3.dp)
                                .graphicsLayer(alpha = 0.99f)
                                .drawWithCache {
                                    onDrawWithContent {
                                        drawContent()
                                        drawRect(
                                            brush = Brush.horizontalGradient(
                                                listOf(
                                                    LightBlue,
                                                    Color(0xFFFFFF),
                                                    Color(0xFFFFFF)
                                                )
                                            ),
                                            blendMode = BlendMode.SrcAtop
                                        )
                                    }
                                }

                        )
                    }
                    else{
                        Icon(
                            painter = painterResource(id = R.drawable.star),
                            contentDescription = "Filled star",
                            tint = Color.White,
                            modifier = Modifier
                                .padding(end = 3.dp)

                        )
                    }
                }

            }

        }

    }

}

@OptIn(ExperimentalFoundationApi::class, ExperimentalStdlibApi::class)
@Composable
fun Content(movie: Movie){

    var defaultColor = Color(0x99FFFFFF)
    var activeColor = Color(0xFFFFFFFF)

    var activeHeader by remember { mutableStateOf("Overview") }
    var headerList by remember { mutableStateOf(listOf("Trailer","Overview","Details")) }
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = {MovieDetailPages.values().size})
    val selectedTabIndex = remember{ derivedStateOf { pagerState.currentPage }}

    var videoState by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(vertical = 30.dp)
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex.value,
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.Transparent
        ) {
            MovieDetailPages.values().forEachIndexed { index, currentTab ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    selectedContentColor = activeColor,
                    unselectedContentColor = defaultColor,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(currentTab.ordinal)
                        }
                    },
                    text = {
                        Text( text = currentTab.pageName)
                    }
                )
            }
        }


        HorizontalPager(
            state = pagerState,

        ) {
            var current = MovieDetailPages.values()[selectedTabIndex.value]

            when(current){
                MovieDetailPages.Overview -> Overview(overview = movie.overview)
                MovieDetailPages.Detail -> Detail()
                MovieDetailPages.Trailer -> movie.videos?.let { Trailers(it) }

            }
        }


    }
}

@Composable
fun Trailers(videos : Videos){

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(top = 25.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var trailer = videos.results.find { it.name == "Official Trailer" }

        trailer?.let {
            Text(
                modifier = Modifier
                    .padding(bottom = 10.dp, start = 10.dp),
                text = "Trailer",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = Color(0xB3FFFFFF)
                )
            )

            YouTubePlayer(
                videoId = it.key,
                lifecycleOwner = LocalLifecycleOwner.current
            )
        }


    }

}

@Composable
fun Overview(overview : String){

    Text(
        text = overview,
        modifier = Modifier
            .padding(top = 25.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Justify,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Light,
            lineHeight = 30.sp,
            fontSize = 16.sp
        )
    )

}

@Composable
fun Detail(){

}

@Composable
fun Bottom(){

}