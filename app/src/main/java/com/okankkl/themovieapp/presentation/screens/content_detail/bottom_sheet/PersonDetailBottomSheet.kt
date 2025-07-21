package com.okankkl.themovieapp.presentation.screens.content_detail.bottom_sheet

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.okankkl.themovieapp.data.model.dto.Person
import com.okankkl.themovieapp.presentation.components.ContentPoster
import com.okankkl.themovieapp.ui.theme.BottomSheetBgColor
import com.okankkl.themovieapp.utils.DateUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonDetailBottomSheet(
    modifier: Modifier = Modifier,
    person: Person,
    onDismissClick: () -> Unit
){
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        sheetState = bottomSheetState,
        containerColor = BottomSheetBgColor,
        onDismissRequest = onDismissClick
    ){
        LazyColumn(
          modifier = modifier
              .fillMaxWidth()
              .padding(horizontal = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            person.profilePath?.let {
                item {
                    ContentPoster(
                        modifier = modifier,
                        posterPath = it,
                        contentScale = ContentScale.FillHeight
                    )
                }
            }

            item {
                Text(
                    modifier = modifier
                        .padding(top = 10.dp),
                    text = person.name ?: "",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }

            item{
                Row{
                    Text(
                        text = DateUtils.formatDateString(person.birthday),
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.LightGray
                        )
                    )
                    Text(
                        text = if(person.deathDay != null ) " - ${DateUtils.formatDateString(person.deathDay)}" else "",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.LightGray
                        )
                    )
                }
            }

            item {
                Text(
                    modifier = modifier.padding(top = 10.dp),
                    text = person.biography ?: "",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )
                )
            }
        }
    }
}