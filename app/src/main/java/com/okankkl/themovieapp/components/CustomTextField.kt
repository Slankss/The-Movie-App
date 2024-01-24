package com.okankkl.themovieapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchTextField(
    modifier: Modifier,
    hint : String,
    value : String,
    onValueChange : (String) -> Unit,
    onDone : () -> Unit
){
    
    BasicTextField(
        modifier = modifier
            .background(
                color = Color(0x24FFFFFF),
                shape = RoundedCornerShape(12.dp)
            ),
        value = value,
        singleLine = true,
        cursorBrush = SolidColor(Color(0x80FFFFFF)),
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onDone()
            }
        ),
        onValueChange = {
            onValueChange(it)
        },
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                ){
                    if(value.isEmpty()){
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterStart),
                            text = hint,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color(0x80FFFFFF)
                            )
                        )
                    }
                    innerTextField()
                }
            }
        }
    )



}



