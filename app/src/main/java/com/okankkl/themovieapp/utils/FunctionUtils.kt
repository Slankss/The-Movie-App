package com.okankkl.themovieapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

class FunctionUtils {
    companion object {
        fun getType(type: String): ContentType {
            return when (type) {
                ContentType.Movie.path -> ContentType.Movie
                else -> ContentType.TvSeries
            }
        }

        fun getCategory(category: String): Categories {
            return when (category) {
                Categories.Trending.path -> Categories.Trending
                Categories.TopRated.path -> Categories.TopRated
                Categories.NowPlaying.path -> Categories.NowPlaying
                Categories.OnTheAir.path -> Categories.OnTheAir
                else -> Categories.Popular
            }
        }

        @Composable
        fun isNetworkAvailable(): Boolean {

            val context = LocalContext.current
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

            val networkCapabilities = connectivityManager?.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }

        fun getImageUrl(path: String?): String {
            return "${Constants.IMAGE_BASE_URL}$path"
        }
    }
}
