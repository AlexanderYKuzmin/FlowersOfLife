package com.kuzmin.flowersoflife.ui.state

import androidx.compose.runtime.Immutable
import com.kuzmin.flowersoflife.core.domain.model.User

sealed interface AppUiState {
    data object Loading : AppUiState

    data class Error(val message: String) : AppUiState

    @Immutable
    data class Success(
        val isLogoutButtonVisible: Boolean = false,
        val isLoading: Boolean = false,
        val isInternetAvailable: Boolean = true,
        val user: User? = null,
        val isAuthorized: Boolean = false,

        val title: String = "",
        val profilePhotoUrl: String? = null,
        val showBackButton: Boolean = true,
        val showProfilePhoto: Boolean = false,
        val showSettingsButton: Boolean = false
    ) : AppUiState
}