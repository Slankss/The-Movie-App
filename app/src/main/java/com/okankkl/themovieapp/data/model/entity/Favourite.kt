package com.okankkl.themovieapp.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favourites")
data class Favourite(
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    var contentType : String? = null,
    var title : String? = null,
    var backdropPath : String? = null,
    var posterPath : String? = null,
    var contentId : Int? = null,
    var date : String? = null,
    var imdb : Double? = null,
    var time : String? = null
)