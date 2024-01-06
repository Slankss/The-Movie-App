package com.okankkl.themovieapp.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.enum_sealed.Categories
import com.okankkl.themovieapp.enum_sealed.Pages
import com.okankkl.themovieapp.enum_sealed.Resources
import com.okankkl.themovieapp.model.TvSeries
import com.okankkl.themovieapp.ui.theme.LightBlue
import com.okankkl.themovieapp.util.Util
import com.okankkl.themovieapp.viewModel.listViewModel
import com.okankkl.themovieapp.components.*
import com.okankkl.themovieapp.enum_sealed.DataType

@Composable
fun TvSeriesList(navController: NavController,listViewModel: listViewModel){

    val popularTvSeries = listViewModel.popularTvSeries.collectAsState()
    val trendTvSeries = listViewModel.trendTvSeries.collectAsState()
    val topRatedSeries = listViewModel.topRatedTvSeries.collectAsState()
    val onTheAirTvSeries = listViewModel.onTheAirTvSeries.collectAsState()

    fun currentList(tvSeriesType: Categories) : Resources?
    {
        return when(tvSeriesType){
            Categories.Popular -> popularTvSeries.value
            Categories.Trending -> trendTvSeries.value
            Categories.TopRated -> topRatedSeries.value
            Categories.OnTheAir -> onTheAirTvSeries.value
            else -> null
        }
    }

    SideEffect {
        listViewModel.getTvSeries()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Categories.values().forEach { tvSeriesType ->
            val currentState = currentList(tvSeriesType)
            if(currentState != null){
                when(currentState){
                    is Resources.Loading -> Loading()
                    is Resources.Success -> {
                        if(tvSeriesType == Categories.Trending){
                            TrendTvSeries(
                                tvSeries = (currentState as Resources.Success).data as List<TvSeries>,
                                navController = navController
                            )
                        }
                        else{
                            TvSeriesList(
                                tvSeries = (currentState as Resources.Success).data as List<TvSeries>,
                                tvSeriesType = tvSeriesType,
                                navController)
                        }
                    }
                    is Resources.Failed -> {
                        Failed(
                            errorMsg = (currentState as Resources.Failed).errorMsg
                        )
                    }
                }
            }

        }

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrendTvSeries(tvSeries : List<TvSeries>, navController: NavController){

    var time by remember { mutableStateOf(0) }

    var pageState = rememberPagerState(
        pageCount = {
            tvSeries.size
        },
        initialPage = 0,

        )

    LaunchedEffect(key1 = time, block = {
        /*
        while(true){
            if(time == 5){
                pageState.animateScrollToPage(pageState.currentPage +1)
                time = 0
            }
            delay(1000L)
            time++
        }

         */
    })
    Column(
        modifier = Modifier

    ) {
        HorizontalPager(
            state = pageState,
            modifier = Modifier.fillMaxWidth()
        )
        { page ->

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(12.dp)
                    ),
            ){
                AsyncImage(
                    model = Util.IMAGE_BASE_URL +tvSeries[page].backdropPath,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            navController.navigate("${Pages.TvSeriesDetail.route}/${tvSeries[page].id}")
                        },
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = tvSeries[page].name,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .background(
                            color = Color(0x4D012022)
                        )
                        .fillMaxWidth()
                        .padding(5.dp),
                    style = MaterialTheme.typography.headlineLarge.copy(

                    ),


                    )
            }
        }
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 10.dp, top = 20.dp),
            horizontalArrangement = Arrangement.Center
        ){
            repeat(pageState.pageCount) { iteration ->
                val color = if(pageState.currentPage == iteration) LightBlue else Color(0x24FFFFFF)
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clip(CircleShape)
                        .background(
                            color = color
                        )
                        .size(8.dp)
                )
            }
        }
    }

}

@Composable
fun TvSeriesList(tvSeries : List<TvSeries>, tvSeriesType: Categories , navController: NavController){

    Column(
        modifier = Modifier
            .padding()
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ){
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 15.dp),
                text = tvSeriesType.title,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 16.sp
                )
            )
            Text(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 15.dp)
                    .clickable {
                        navController.navigate("${Pages.ViewAll.route}/${DataType.TvSeries().name}&${tvSeriesType.title}")
                    },
                text = "view all",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 14.sp,
                    color = Color(0xB3FFFFFF)
                )
            )
        }
        LazyRow(
            modifier = Modifier
        ){
            itemsIndexed(tvSeries){index ,tvSeries ->
                if(tvSeries.posterPath != null && tvSeries.posterPath!!.isNotEmpty()){
                    Poster(
                        posterPath = tvSeries.posterPath!!,
                        id = tvSeries.id,
                        modifier = Modifier
                            .height(150.dp)
                            .padding(
                                start = if(index == 0) 15.dp else 0.dp,
                                end = 15.dp
                            )
                    ){ tvSeriesId ->
                        navController.navigate("${Pages.TvSeriesDetail.route}/${tvSeriesId}")
                    }
                }
            }
        }
    }
}




