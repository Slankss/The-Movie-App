package com.okankkl.themovieapp.model

import com.google.gson.annotations.SerializedName

data class Display(
    var id : Int,
    @SerializedName("poster_path")
    var posterPath : String?,
    @SerializedName("backdrop_path")
    var backdropPath : String?,
    @SerializedName("vote_average")
    var voteAverage : Double
){
    var videos : Videos? = null


}
