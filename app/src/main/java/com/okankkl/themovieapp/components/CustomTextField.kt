package com.okankkl.themovieapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.ui.theme.TheMovieAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    modifier: Modifier,
    hint : String,
    value : String,
    onValueChange : (String) -> Unit

){
    
    BasicTextField(
        modifier = modifier
            .background(
                color = Color(0x24FFFFFF),
                shape = RoundedCornerShape(12.dp)
            ),
        value = value,
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Medium
        ),
        onValueChange = {
            onValueChange(it)
        },
        decorationBox = { innerTextField ->
            ConstraintLayout(
                modifier = Modifier
                    .padding(10.dp)
            ) {

                val (search,icon) = createRefs()

                Box(
                    modifier = Modifier
                        .constrainAs(search){
                            start.linkTo(parent.start)
                            end.linkTo(icon.start, margin = 10.dp)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.fillToConstraints
                        }
                ){
                    if(value.isEmpty()){
                        Text(
                            modifier = Modifier,
                            text = hint,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color(0xB3FFFFFF)
                            )
                        )
                    }
                    innerTextField()
                }

                Icon(
                    painterResource(id = R.drawable.ic_search),
                    tint = Color.White,
                    modifier = Modifier
                        .constrainAs(icon){
                              end.linkTo(parent.end)
                              top.linkTo(parent.top)
                              bottom.linkTo(parent.bottom)
                        },
                    contentDescription = "Favourites"
                )
            }

        }
    )




}

