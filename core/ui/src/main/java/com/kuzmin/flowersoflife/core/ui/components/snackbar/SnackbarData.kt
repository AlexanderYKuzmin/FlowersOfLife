package com.kuzmin.flowersoflife.core.ui.components.snackbar

data class SnackbarData(
    val message: String,
    val type: SnackbarMessageType = SnackbarMessageType.INFO
)