package com.okankkl.themovieapp.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.components.SearchTextField
import com.okankkl.themovieapp.components.TopMenuItem
import com.okankkl.themovieapp.enum_sealed.DisplayType
import com.okankkl.themovieapp.enum_sealed.Pages
import com.okankkl.themovieapp.ui.theme.BacgroundTransparentColor
import com.okankkl.themovieapp.ui.theme.BackgroundColor
import com.okankkl.themovieapp.viewModel.ListViewModel

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun Home(navController: NavController)
{
    val listViewModel : ListViewModel = hiltViewModel()
    val selectedPage = listViewModel.selectedPage.collectAsState()
    var menuVisibility by  remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()


        ) {
            when(selectedPage.value){
                DisplayType.TvSeries -> TvSeriesList(navController = navController,listViewModel = listViewModel,topPadding = 100.dp)
                else -> MovieList(navController = navController,listViewModel = listViewModel,topPadding = 100.dp){ state ->
                    menuVisibility = state
                }
            }
        }

        TopMenu(selectedPage,menuVisibility){ page ->
            listViewModel.setSelectedPage(page)
        }

    }
}

@Composable
fun TopMenu(selectedPage : State<DisplayType>,menuVisibility : Boolean,setSelectedPage :(DisplayType) -> Unit){

    val menuList = Pages.values().filter { it == Pages.MovieList || it == Pages.TvSeriesList }
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
                tint = Color.White
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

