package com.okankkl.themovieapp.view

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.components.SearchTextField
import com.okankkl.themovieapp.enum_sealed.Pages
import com.okankkl.themovieapp.viewModel.ListViewModel

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun Home(navController: NavController)
{
    val listViewModel : ListViewModel = hiltViewModel()
    var selectedPage = listViewModel.selectedPage.collectAsState()

    LaunchedEffect(true){
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopMenu(selectedPage){ page ->
            listViewModel.setSelectedPage(page)
        }

        AnimatedContent(
            targetState = selectedPage,
            label = "Home"
        ) {
            when(selectedPage.value){
                Pages.TvSeriesList -> TvSeriesList(navController = navController,listViewModel = listViewModel)
                else -> MovieList(navController = navController,listViewModel = listViewModel)
            }
        }

    }
}

@Composable
fun TopMenu(selectedPage : State<Pages>,setSelectedPage :(Pages) -> Unit){
    Row(
        modifier = Modifier
            .padding(top = 25.dp)
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(14.dp))
                .background(
                    color = if (selectedPage.value == Pages.MovieList) Color.White else Color.Transparent
                )
                .clickable {
                    setSelectedPage(Pages.MovieList)
                }
        ){
            Text(
                text = Pages.MovieList.title,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 25.dp, vertical = 10.dp),
                style = MaterialTheme.typography.labelLarge.copy(
                    color = if(selectedPage.value == Pages.MovieList) Color.Black else Color.White,
                    fontSize = 16.sp
                )
            )
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(14.dp))
                .background(
                    color = if (selectedPage.value == Pages.TvSeriesList) Color.White else Color.Transparent
                )
                .clickable {
                    setSelectedPage(Pages.TvSeriesList)
                }
        ){
            Text(
                text = Pages.TvSeriesList.title,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 25.dp, vertical = 10.dp),
                style = MaterialTheme.typography.labelLarge.copy(
                    color = if(selectedPage.value == Pages.TvSeriesList) Color.Black else Color.White,
                    fontSize = 16.sp
                )
            )
        }
    }
}

@Composable
fun Search(){

    var search by remember { mutableStateOf("")}

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 20.dp),

        ){
        val (searchField,favourites) = createRefs()
        SearchTextField(
            modifier = Modifier
                .constrainAs(searchField){
                    top.linkTo(parent.top)
                    end.linkTo(favourites.start, margin = 10.dp)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    width = Dimension.fillToConstraints

                },
            hint = "Which movie are you looking for?",
            value = search,
            onValueChange = {
                search = it
            }
        )
        Box(
            modifier = Modifier
                .constrainAs(favourites) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .background(
                    color = Color(0x24FFFFFF),
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable {

                }
        ){
            Icon(
                painterResource(id = R.drawable.ic_fav_unselected),
                tint = Color.White,
                contentDescription = "Favourites",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(10.dp)
            )
        }

    }

}

