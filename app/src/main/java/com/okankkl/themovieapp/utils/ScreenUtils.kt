package com.okankkl.themovieapp.utils

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController

class ScreenUtils {
    companion object {

        fun setScreenOrientation(context: Context, isLandscape: Boolean = true){
            val activity = context as? Activity
            activity?.requestedOrientation = if (isLandscape){
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            } else {
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }

        fun setVisibilityOfSystemUi(context: Context, isVisible: Boolean){
            val activity = context as? Activity

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                activity?.window?.insetsController?.let { controller ->
                    if (isVisible){
                        controller.show(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                    } else {
                        controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                        controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                    }
                }
            } else {
                @Suppress("DEPRECATION")
                activity?.window?.decorView?.systemUiVisibility = if (isVisible) {
                    // Show System UI
                    View.SYSTEM_UI_FLAG_VISIBLE
                } else {
                    // Hide System UI
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                }
            }
        }
    }
}