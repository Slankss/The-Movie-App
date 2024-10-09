package com.okankkl.themovieapp.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("TvSeries")
data class TvSeriesEntity(
    @PrimaryKey
    val localId: Int? = null,
    val id: Int? = null,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    val title: String? = null,
    val category: String? = null
)