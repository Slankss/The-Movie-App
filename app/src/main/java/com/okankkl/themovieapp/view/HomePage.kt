package com.okankkl.themovieapp.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.components.Loading
import com.okankkl.themovieapp.components.Poster
import com.okankkl.themovieapp.components.TopMenuItem
import com.okankkl.themovieapp.enum_sealed.Categories
import com.okankkl.themovieapp.enum_sealed.DisplayType
import com.okankkl.themovieapp.enum_sealed.Pages
import com.okankkl.themovieapp.model.Display
import com.okankkl.themovieapp.ui.theme.BacgroundTransparentColor
import com.okankkl.themovieapp.ui.theme.LightBlue
import com.okankkl.themovieapp.util.Util
import com.okankkl.themovieapp.viewModel.ListViewModel

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun Home(navController: NavController)
{
    val listViewModel : ListViewModel = hiltViewModel()
    val selectedPage = listViewModel.selectedPage.collectAsState()
    var menuVisibility by  remember { mutableStateOf(true) }

    val loadingState = listViewModel.loadingState.collectAsState()
    val scrollState = rememberScrollState()

    val allDisplayList = listViewModel.allDisplayList.collectAsState()

    LaunchedEffect(key1 = true){
        if(selectedPage.value == DisplayType.Movie)
            listViewModel.getMovies()
        else
            listViewModel.getTvSeries()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
            ,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            listViewModel.setLoadingState(allDisplayList.value.isEmpty())

            Categories.values().forEach { type ->
                val displayList = allDisplayList.value
                    .filter { it.category.split(",").contains(type.path)}
                    .sortedByDescending { it.popularity }
                    .distinctBy { it.id }

                if(displayList.isNotEmpty()){
                    if(type == Categories.Trending){
                        TrendDisplayList(displays = displayList,displayType = selectedPage.value.path, navController = navController, topPadding = 100.dp)
                    } else {
                        DisplayContentList(displays = displayList,displayType = selectedPage.value.path, moviesType = type, navController = navController)
                    }
                }
            }
        }

        TopMenu(navController,selectedPage,menuVisibility){ page ->
            listViewModel.setSelectedPage(page)
        }

        if(loadingState.value)
            Loading(
                modifier = Modifier
                    .align(Alignment.Center)
            )

    }
}

@Composable
fun TopMenu(navController: NavController,selectedPage : State<DisplayType>,menuVisibility : Boolean,setSelectedPage :(DisplayType) -> Unit){

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = BacgroundTransparentColor)
            .padding(top = 20.dp, start = 25.dp, end = 25.dp)
            .pointerInput(Unit) {
                detectTapGestures {
                }
            },
        verticalArrangement = Arrangement.spacedBy(10.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Box(
                modifier = Modifier
                    .height(25.dp)
                    .width(25.dp)
                    .background(color = Color.White)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "search",
                tint = Color.White,
                modifier = Modifier
                    .size(26.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        navController.navigate(Pages.Search.route)
                    }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            DisplayType.values().forEachIndexed { index, menu ->
                val isSelected = selectedPage.value == menu
                TopMenuItem(displayType = menu, isSelected = isSelected){
                    setSelectedPage(menu)
                }
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrendDisplayList(displays : List<Display>,displayType : String, navController: NavController, topPadding: Dp){

    var time by remember { mutableStateOf(0) }

    var pageState = rememberPagerState(
        pageCount = {
            displays.size
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
            .padding(top = topPadding, start = 15.dp,end = 15.dp)
    ) {
        HorizontalPager(
            state = pageState,
            modifier = Modifier.fillMaxWidth()
        )
        { page ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp, start = 10.dp)
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(12.dp)
                    ),
            ){
                val display = displays[page]
                AsyncImage(
                    model = Util.IMAGE_BASE_URL +display.backdropPath,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            val route = "${Pages.DisplayDetail.route}/${display.id}&$displayType"
                            navController.navigate(route)
                        },
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = display.en_title,
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
fun DisplayContentList(displays : List<Display>,displayType: String,moviesType : Categories, navController: NavController){

    Column(
        modifier = Modifier
            .padding(bottom = 10.dp)
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
                text = moviesType.title,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 16.sp
                )
            )
            Text(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 15.dp)
                    .clickable {
                        navController.navigate("${Pages.ViewAll.route}/$displayType&${moviesType.path}")
                    },
                text = "view all",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 12.sp,
                    color = Color(0xB3FFFFFF)
                )
            )
        }
        LazyRow(
            modifier = Modifier
        ){
            itemsIndexed(displays){ index, display ->
                if(display.posterPath != null && display.posterPath!!.isNotEmpty()){
                    Poster(
                        posterPath = display.posterPath!!,
                        id = display.id,
                        modifier = Modifier
                            .height(150.dp)
                            .padding(
                                start = if (index == 0) 15.dp else 0.dp,
                                end = 15.dp
                            )

                    ){ displayId ->
                        val route = "${Pages.DisplayDetail.route}/${display.id}&$displayType"
                        navController.navigate(route)
                    }
                }
            }
        }
    }
}

