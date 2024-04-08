package com.okankkl.themovieapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.okankkl.themovieapp.data.remote.dto.Videos

@Entity("Display")
open class Content(
    @PrimaryKey
    var id : Int,
    @ColumnInfo(defaultValue = "")
    var backdropPath : String?,
    @ColumnInfo(defaultValue = "")
    var posterPath : String?,
    @ColumnInfo(defaultValue = "")
    var mediaType : String?
){
    @Ignore
    open var videos : Videos? = null
    var title : String = ""
    var releaseDate : String = ""
    @ColumnInfo(defaultValue = "")
    var category  : String = ""
    var popularity : Double = 0.0
    var voteAverage : Double = 0.0

    constructor(id: Int,backdropPath: String?,posterPath: String?,title: String,mediaType: String?,
        releaseDate : String,category : String,popularity : Double,voteAverage : Double)
        :this(id,backdropPath,posterPath,mediaType){
            this.title = title
            this.releaseDate = releaseDate
            this.category = category
            this.popularity = popularity
            this.voteAverage = voteAverage
    }

}
