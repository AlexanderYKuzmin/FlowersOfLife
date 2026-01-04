package com.kuzmin.flowersoflife.core.ui.components.snackbar

import com.kuzmin.flowersoflife.core.ui.event.UiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class SnackbarControllerImpl @Inject constructor(
    private val uiEventFlow: MutableSharedFlow<UiEvent>
) : SnackbarController {

    override suspend fun showMessage(message: String, type: SnackbarMessageType) {
        uiEventFlow.emit(UiEvent.ShowSnackbar(message, type))
    }
}