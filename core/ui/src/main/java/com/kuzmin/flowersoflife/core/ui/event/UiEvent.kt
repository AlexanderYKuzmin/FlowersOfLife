package com.kuzmin.flowersoflife.core.ui.event

import com.kuzmin.flowersoflife.common.model.TopBarUiSettings
import com.kuzmin.flowersoflife.core.ui.components.snackbar.SnackbarMessageType

sealed class UiEvent {
    data class ShowSnackbar(
        val message: String,
        val type: SnackbarMessageType = SnackbarMessageType.INFO
    ) : UiEvent()

    data class UpdateAppState(
        val topBarUiSettings: TopBarUiSettings,
        val isBottomNavVisible: Boolean = true
    ) : UiEvent()
}
