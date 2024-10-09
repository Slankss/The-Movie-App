package com.okankkl.themovieapp.utils

import java.time.LocalDateTime

fun String?.toLocalDateTime(): LocalDateTime? {
    return when(this){
        null -> null
        else -> LocalDateTime.parse(this)
    }
}