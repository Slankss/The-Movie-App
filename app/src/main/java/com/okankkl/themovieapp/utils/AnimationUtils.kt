package com.okankkl.themovieapp.utils

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.Alignment

class AnimationUtils {
    companion object {
        //(AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null
        private const val animDuration = 400
        fun exitAnimRightToLeft() : ExitTransition {
            return fadeOut(
                animationSpec = tween(
                    animDuration, easing = LinearEasing
                )
            ) + slideOutHorizontally(
                animationSpec = tween(
                    durationMillis = animDuration,
                    easing = EaseOut
                ),
            )
        }

        fun exitAnimLeftToRight() : ExitTransition {
            return fadeOut(
                animationSpec = tween(
                    animDuration, easing = LinearEasing
                )
            ) + slideOutHorizontally(
                animationSpec = tween(
                    durationMillis = animDuration,
                    easing = EaseIn
                ),
            )
        }

        fun enterAnimLeftToRight() : EnterTransition {
            return fadeIn(
                animationSpec = tween(
                    animDuration, easing = LinearEasing
                )
            ) + slideInHorizontally(
                animationSpec = tween(
                    durationMillis = animDuration,
                    easing = EaseIn,
                ),
            )
        }

        fun enterAnimRightToLeft() : EnterTransition {
            return fadeIn(
                animationSpec = tween(
                    animDuration, easing = LinearEasing
                )
            ) + slideInHorizontally(
                animationSpec = tween(
                    durationMillis = animDuration,
                    easing = EaseOut,
                ),
            )
        }

        fun enterTransitionExpandVertically() : EnterTransition {
            return scaleIn() + expandVertically(expandFrom = Alignment.CenterVertically)
        }

        fun exitTransitionShrinkVertically() : ExitTransition {
            return scaleOut() + shrinkVertically(shrinkTowards = Alignment.CenterVertically)
        }


    }
}