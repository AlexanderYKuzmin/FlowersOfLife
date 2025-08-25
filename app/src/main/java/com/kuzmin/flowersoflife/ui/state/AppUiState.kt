package com.kuzmin.flowersoflife.ui.state

import androidx.compose.runtime.Immutable
import com.kuzmin.flowersoflife.common.model.AppUiData
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

        val appUiData: AppUiData
    ) : AppUiState

}