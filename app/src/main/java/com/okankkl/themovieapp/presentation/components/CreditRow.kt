package com.okankkl.themovieapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.common.Constants
import com.okankkl.themovieapp.data.remote.dto.CreatedBy
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreditRow(createdtBy : List<CreatedBy>){

    Text(
        text = "Created By",
        style = MaterialTheme.typography.labelLarge.copy(
            fontSize = 16.sp
        ),
        modifier = Modifier
    )

    FlowRow(
        maxItemsInEachRow = 2,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
    ) {
        createdtBy.forEach { createdBy ->
            Row(
                modifier = Modifier
                    .padding(bottom = 10.dp, end = 25.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                if( createdBy.profile_path == null || createdBy.profile_path!!.isEmpty()){
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF363636))
                    )
                }
                else{
                    AsyncImage(
                        model = Constants.IMAGE_BASE_URL +createdBy.profile_path,
                        contentDescription = "Movie Poster",
                        placeholder = painterResource(id = R.drawable.ic_launcher_background),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                }

                Text(
                    text = createdBy.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 14.5.sp,
                        color = Color(0xFFBCBCBC)
                    ),
                    modifier = Modifier
                        .padding(start = 5.dp)
                )
            }
        }
    }
}