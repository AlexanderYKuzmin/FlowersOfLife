package com.kuzmin.flowersoflife.core.ui.components.snackbar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals

class TypedSnackbarVisuals(
    override val message: String,
    val type: SnackbarMessageType
) : SnackbarVisuals {
    override val actionLabel: String? = null
    override val duration: SnackbarDuration = SnackbarDuration.Short
    override val withDismissAction: Boolean = false
}
