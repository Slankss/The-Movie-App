package com.okankkl.themovieapp.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.okankkl.themovieapp.SessionViewModel
import com.okankkl.themovieapp.utils.FunctionUtils
import com.okankkl.themovieapp.presentation.components.InternetConnectionError
import com.okankkl.themovieapp.ui.theme.BgColor
import com.okankkl.themovieapp.ui.theme.TheMovieAppTheme
import com.okankkl.themovieapp.presentation.navigation.BaseNavigation
import com.okankkl.themovieapp.presentation.navigation.BottomMenuNavigation
import com.okankkl.themovieapp.presentation.screens.home.dialog.PosterDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieAppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    if (FunctionUtils.isNetworkAvailable()) {
                        TheMovieApp()
                    } else {
                        InternetConnectionError()
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation", "RestrictedApi")
@Composable
fun TheMovieApp() {

    val sessionViewModel : SessionViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val snackBarHost = remember { SnackbarHostState() }

    val currentDestination = navController.currentBackStackEntryFlow.collectAsState(initial = navController.currentBackStackEntry)
    Scaffold(
        modifier = Modifier,
        containerColor = BgColor,
        snackbarHost = {
            SnackbarHost(hostState = snackBarHost)
        },
        bottomBar = {
            currentDestination.value?.destination?.let { destination ->
                if (destination.route != Screen.Splash.route) {
                    BottomMenuNavigation(navController)
                }
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding),
            color = BgColor
        ) {
            BaseNavigation(
                navController = navController,
                sessionViewModel = sessionViewModel,
                showMessage = { message ->
                    scope.launch {
                        snackBarHost.showSnackbar(message = message)
                    }
                }
            )
            sessionViewModel.viewState.collectAsState().value.apply {
                if(showPosterDialog && currentPosterPath != null){
                    PosterDialog(
                        onDismiss = {
                            sessionViewModel.changePosterDialogState(false,null)
                        },
                        posterPath = currentPosterPath
                    )
                }
            }
        }
    }
}