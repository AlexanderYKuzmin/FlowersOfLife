package com.kuzmin.flowersoflife.core.ui.components.snackbar

interface SnackbarController {
    suspend fun showMessage(message: String, type: SnackbarMessageType = SnackbarMessageType.INFO)
}
