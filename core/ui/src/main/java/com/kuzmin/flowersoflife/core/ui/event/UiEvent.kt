package com.kuzmin.flowersoflife.core.ui.event

import com.kuzmin.flowersoflife.common.model.AppUiData
import com.kuzmin.flowersoflife.core.ui.components.snackbar.SnackbarMessageType

sealed class UiEvent {
    data class ShowSnackbar(
        val message: String,
        val type: SnackbarMessageType = SnackbarMessageType.INFO
    ) : UiEvent()

    data class Navigate(val route: String) : UiEvent()

    data class UpdateAppState(val appUiData: AppUiData) : UiEvent()
}
