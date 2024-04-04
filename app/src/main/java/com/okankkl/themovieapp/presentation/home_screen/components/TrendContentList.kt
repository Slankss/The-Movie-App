package com.okankkl.themovieapp.presentation.home_screen.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.okankkl.themovieapp.common.Constants
import com.okankkl.themovieapp.domain.model.Content
import com.okankkl.themovieapp.presentation.Pages
import com.okankkl.themovieapp.ui.theme.LightBlue
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrendContentList(
    contents: List<Content>, displayType: String, navController: NavController, topPadding: Dp
) {

    var time by remember { mutableIntStateOf(0) }

    var pageState = rememberPagerState(
        pageCount = { contents.size },
        initialPage = 0,
    )

    LaunchedEffect(key1 = time) {
        if(contents.isNotEmpty()){
            while (true) {
                delay(1000)
                if (time % 5 == 0) {
                    if (pageState.currentPage == contents.size - 1) pageState.scrollToPage(0)
                    else pageState.animateScrollToPage(pageState.currentPage + 1)
                }
                time++
            }
        }
    }
    Column(
        modifier = Modifier.padding(top = topPadding, start = 15.dp, end = 15.dp)
    ) {
        HorizontalPager(
            state = pageState,
            modifier = Modifier.fillMaxWidth(),
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp, start = 10.dp)
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .shadow(
                        elevation = 4.dp, shape = RoundedCornerShape(12.dp)
                    ),
            ) {
                val display = contents[page]
                AsyncImage(
                    model = Constants.IMAGE_BASE_URL + display.backdropPath,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .combinedClickable(onClick = {
                            val route = "${Pages.DisplayDetail.route}/${display.id}&$displayType"
                            navController.navigate(route)
                        }, onLongClick = {
                            time = 0
                        }),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = display.title,
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
                .padding(bottom = 10.dp, top = 20.dp), horizontalArrangement = Arrangement.Center
        ) {
            repeat(pageState.pageCount) { iteration ->
                val color = if (pageState.currentPage == iteration) LightBlue else Color(0x24FFFFFF)
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