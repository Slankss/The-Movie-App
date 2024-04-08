package com.okankkl.themovieapp.domain.extensions

import android.annotation.SuppressLint
import java.time.LocalDate
import java.util.Locale

@SuppressLint("SimpleDateFormat")
fun convertDate(dateString : String) : String{
    val date = LocalDate.parse(dateString)
    val month = date.month.name.lowercase(Locale.ROOT)
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
    return "$month ${date.dayOfMonth}, ${date.year}"

}