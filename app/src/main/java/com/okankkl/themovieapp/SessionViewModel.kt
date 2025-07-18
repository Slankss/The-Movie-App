package com.okankkl.themovieapp

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
) : ViewModel() {

    private var _viewState = MutableStateFlow(SessionState())
    val viewState = _viewState.asStateFlow()

    fun changePosterDialogState(state: Boolean,posterPath: String?) {
        _viewState.update {
            it.copy(
                showPosterDialog = state,
                currentPosterPath = posterPath
            )
        }
    }

    fun changeMenuVisibility(state: Boolean) {
        _viewState.update {
            it.copy(
                menuVisibility = state
            )
        }
    }

}
data class SessionState(
    val errorMessage: String? = null,
    val showPosterDialog: Boolean = false,
    val currentPosterPath: String? = null,
    val menuVisibility: Boolean = true,
)