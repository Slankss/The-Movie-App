package com.okankkl.themovieapp.presentation.screens.content_detail.dialog

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.data.model.dto.Images
import com.okankkl.themovieapp.utils.FunctionUtils

@Composable
fun ImageDialog(
    modifier: Modifier = Modifier,
    images: Images,
    onDismiss: () -> Unit
){
    var currentImageIndex by remember{ mutableIntStateOf(0) }

    Dialog(
        onDismissRequest = onDismiss
    ){
        Column(
            modifier = modifier
        ) {
            images.backdrops?.let { images ->
                AsyncImage(
                    modifier = modifier.fillMaxWidth().height(200.dp),
                    model = FunctionUtils.getImageUrl(images[currentImageIndex].filePath) ,
                    contentDescription = "Image",
                    placeholder = painterResource(R.drawable.place_holder)
                )
                LazyRow(
                    modifier = modifier.padding(top = 20.dp)
                ){
                    itemsIndexed(images){ index,image ->
                        AsyncImage(
                            modifier = modifier
                                .height(50.dp)
                                .width(100.dp)
                                .padding(end = 10.dp)
                                .clickable {
                                    currentImageIndex = index
                                }
                                .border(
                                    color = if (currentImageIndex == index) Color.Blue else Color.Transparent,
                                    width = if (currentImageIndex == index) 2.dp else 0.dp
                                ),
                            model = FunctionUtils.getImageUrl(images[index].filePath),
                            contentDescription = "Image",
                            placeholder = painterResource(R.drawable.place_holder)
                        )
                    }
                }
            }
        }
    }
}