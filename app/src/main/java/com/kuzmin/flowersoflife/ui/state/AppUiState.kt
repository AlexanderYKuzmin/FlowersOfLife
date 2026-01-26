package com.kuzmin.flowersoflife.ui.state

import androidx.compose.runtime.Immutable
import com.kuzmin.flowersoflife.common.model.TopBarUiSettings
import com.kuzmin.flowersoflife.core.domain.model.aggregate.UserFamily

sealed interface AppUiState {
    data object Loading : AppUiState

    data class Error(val message: String) : AppUiState

    @Immutable
    data class Success(
        val isLogoutButtonVisible: Boolean = false,
        val isLoading: Boolean = false,
        val isInternetAvailable: Boolean = true,
        val userFamily: UserFamily? = null,
        val isAuthorized: Boolean = false,
        val isBottomNavVisible: Boolean = true,

        val topBarUiSettings: TopBarUiSettings
    ) : AppUiState

}