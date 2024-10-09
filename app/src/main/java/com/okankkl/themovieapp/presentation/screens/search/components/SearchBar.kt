package com.okankkl.themovieapp.presentation.screens.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.presentation.components.SearchTextField
@Composable
fun SearchBarUI(searchEvent : (String) -> Unit){

    var search by remember { mutableStateOf("") }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 20.dp),
    ){
        val (searchField,favourites) = createRefs()
        SearchTextField(
            modifier = Modifier
                .height(45.dp)
                .constrainAs(searchField) {
                    top.linkTo(parent.top)
                    end.linkTo(favourites.start, margin = 10.dp)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    width = Dimension.fillToConstraints

                },
            hint = "What are you looking for?",
            value = search,
            onValueChange = {
                search = it

            },
            onDone = {
                searchEvent(search)
            }
        )
        Box(
            modifier = Modifier
                .size(45.dp)
                .constrainAs(favourites) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .background(
                    color = Color(0x24FFFFFF),
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    searchEvent(search)
                }
        ){
            Icon(
                painterResource(id = R.drawable.ic_search),
                tint = Color.White,
                contentDescription = "Search",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(5.dp)
                    .size(24.dp)
            )
        }
    }
}
