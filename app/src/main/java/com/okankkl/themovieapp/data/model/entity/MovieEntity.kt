package com.okankkl.themovieapp.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Movie")
data class MovieEntity(
    @PrimaryKey
    val localId: Int? = null,
    val id: Int? = null,
    val backdropPath: String? = null,
    val posterPath: String? = null,
    val title: String? = null,
    val category: String? = null
)
