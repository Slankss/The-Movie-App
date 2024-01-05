package com.okankkl.themovieapp.extensions

fun String.capitalizeWords() : String
{
    val words = this.split("_")
    val capitalizeWords = words.map {
        it.replaceFirstChar { char -> char.uppercase() }
    }
    return capitalizeWords.joinToString().replace(",","")
}