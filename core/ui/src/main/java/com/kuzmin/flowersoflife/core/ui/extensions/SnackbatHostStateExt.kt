package com.kuzmin.flowersoflife.core.ui.extensions

import androidx.compose.material3.SnackbarHostState
import com.kuzmin.flowersoflife.core.ui.components.snackbar.SnackbarData
import com.kuzmin.flowersoflife.core.ui.components.snackbar.TypedSnackbarVisuals

suspend fun SnackbarHostState.showTypedSnackbar(data: SnackbarData) {
    this.showSnackbar(
        TypedSnackbarVisuals(
            message = data.message,
            type = data.type
        )
    )
}
