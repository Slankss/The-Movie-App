package com.okankkl.themovieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favourites")
data class Favourite(
    var type : String,
    var title : String,
    var backdropPath : String,
    var posterPath : String,
    var contentId : Int,
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
)
