package com.okankkl.themovieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey


data class MovieEntity(
    @PrimaryKey
    var id : Int,
    var backdropPath : String,
    var posterPath : String,
    var title : String,
    var category : String
)

