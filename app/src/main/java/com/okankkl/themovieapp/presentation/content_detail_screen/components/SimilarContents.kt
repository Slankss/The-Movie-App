package com.okankkl.themovieapp.presentation.content_detail_screen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.okankkl.themovieapp.domain.model.Content
import com.okankkl.themovieapp.presentation.Pages

@Composable
fun SimilarContents(similarContent : List<Content>, navController: NavController, displayType: String){
    Text(
        text = "Similar Contents",
        style = MaterialTheme.typography.labelLarge.copy(
            fontSize = 16.sp
        ),
        modifier = Modifier
            .padding(bottom = 15.dp, start = 15.dp)
    )
    LazyRow(
        modifier = Modifier
    ){
        val filterSimilarList = similarContent
            .filter { it.backdropPath != null && it.backdropPath!!.isNotEmpty()}

        itemsIndexed(filterSimilarList){index,display ->

            SimilarContent(
                content = display,
                index = index
            ){ id ->
                navController.navigate("${Pages.DisplayDetail.route}/${id}&$displayType")
            }
        }
    }
}