package com.okankkl.themovieapp.presentation.home_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.okankkl.themovieapp.domain.model.Content
import com.okankkl.themovieapp.presentation.Categories
import com.okankkl.themovieapp.presentation.Pages
import com.okankkl.themovieapp.presentation.components.ContentPoster

@Composable
fun ContentHorizontalList(
    contents: List<Content>,
    displayType: String,
    moviesType: Categories,
    navController: NavController
) {
    Column(
        modifier = Modifier.padding(bottom = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
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
                    }, text = "view all", style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 12.sp, color = Color(0xB3FFFFFF)
                )
            )
        }
        LazyRow(
            modifier = Modifier
        ) {
            itemsIndexed(contents) { index, display ->
                if (display.posterPath != null && display.posterPath!!.isNotEmpty()) {
                    ContentPoster(
                        posterPath = display.posterPath!!,
                        id = display.id,
                        modifier = Modifier
                            .height(150.dp)
                            .padding(
                                start = if (index == 0) 15.dp else 0.dp, end = 15.dp
                            )

                    ) { displayId ->
                        val route = "${Pages.DisplayDetail.route}/${display.id}&$displayType"
                        navController.navigate(route)
                    }
                }
            }
        }
    }
}