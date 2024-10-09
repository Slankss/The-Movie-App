package com.okankkl.themovieapp.utils

import android.annotation.SuppressLint
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Formatter
import java.util.Locale

class DateUtils {
    companion object {

        val formatter = DateTimeFormatter
            .ofPattern("dd MMMM yyyy")
            .withZone(ZoneId.systemDefault())

        @SuppressLint("SimpleDateFormat")
        fun convertDate(dateString : String?) : String {
            try {
                if(dateString == null){
                    return ""
                }
                val date = LocalDate.parse(dateString)
                val month = date.month.name.lowercase(Locale.ROOT)
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                return "$month ${date.dayOfMonth}, ${date.year}"
            } catch (_: Exception){
                return ""
            }
        }

        fun formatDate(dateString : String?) : String {
            try {
                if (dateString == null){
                    return ""
                }
                val instant = Instant.parse(dateString)
                return formatter.format(instant)
            } catch (_: Exception){
                return ""
            }
        }

        fun formatDateString(dateString : String?) : String {
            try {
                if (dateString == null){
                    return ""
                }
                val date = LocalDate.parse(dateString)
                return formatter.format(date)
            } catch (_ : Exception) {
                return ""
            }
        }
    }
}