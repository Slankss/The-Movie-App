package com.okankkl.themovieapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import com.okankkl.themovieapp.components.YouTubePlayer

@Composable
fun MovieDetail(navController: NavController,movieId : Int?)
{

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = 30.dp)
    ) {
        TopHeader(poster = movieId)
        Content()
    }

}

@Composable
fun TopHeader(poster : Int?){

    var movieRate = 7/2

    Row(
       modifier = Modifier
           .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        poster?.let {
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
                Image(
                    painter = painterResource(id = it),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                )
            }

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
                text = "Avengers: End Game",
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
                Text(
                    modifier = Modifier
                        .padding(end = 5.dp),
                    text = "4.0",
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

@Composable
fun Content(){

    var defaultColor = Color(0x99FFFFFF)
    var activeColor = Color(0xFFFFFFFF)

    var activeHeader by remember { mutableStateOf("Overview") }
    var headerList by remember { mutableStateOf(listOf("Trailer","Overview","Details")) }

    Column(
        modifier = Modifier
            .padding(vertical = 30.dp)
    ) {

        Row(
            modifier = Modifier

                .fillMaxWidth()

        ){
            headerList.forEach {
                Column(
                    modifier = Modifier
                        .weight(1f),
                ) {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 16.sp,
                            color = if(it == activeHeader) activeColor else defaultColor
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 10.dp)
                            .clickable {
                                activeHeader = it
                            }
                    )
                    if(it == activeHeader){
                        Divider(
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .height(4.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp)),
                            color = LightBlue
                        )
                    }
                    else{
                        Divider(
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .fillMaxWidth()
                        )
                    }

                }

            }
        }

        when(activeHeader){
            headerList[0] -> Trailers()
            headerList[1] -> Overview()
            headerList[2] -> Detail()
        }

    }
}

@Composable
fun Trailers(){

    Column(
        modifier = Modifier
            .padding(top = 25.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = 10.dp, start = 10.dp),
            text = "Trailer",
            style = MaterialTheme.typography.labelLarge.copy(
                color = Color(0xB3FFFFFF)
            )
        )

        YouTubePlayer(
            videoId = "zSWdZVtXT7E",
            lifecycleOwner = LocalLifecycleOwner.current
        )
    }

}

@Composable
fun Overview(){

    Text(
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt " +
                    "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation " +
                    "ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in " +
                    "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur " +
                    "sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est " +
                    "laborum.",
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