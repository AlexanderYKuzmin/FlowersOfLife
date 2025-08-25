package com.kuzmin.flowersoflife.core.ui.components.snackbarhost

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import com.kuzmin.flowersoflife.core.ui.components.snackbar.SnackbarMessageType
import com.kuzmin.flowersoflife.core.ui.components.snackbar.TypedSnackbarVisuals
import com.kuzmin.flowersoflife.core.ui.theme.ErrorColor
import com.kuzmin.flowersoflife.core.ui.theme.InfoColor
import com.kuzmin.flowersoflife.core.ui.theme.WarningColor

@Composable
fun CustomSnackbarHost(
    hostState: SnackbarHostState
) {
    SnackbarHost(hostState = hostState) { snackbarData ->
        val type = (snackbarData.visuals as? TypedSnackbarVisuals)?.type ?: SnackbarMessageType.INFO

        val backgroundColor = when (type) {
            SnackbarMessageType.INFO -> InfoColor
            SnackbarMessageType.WARNING -> WarningColor
            SnackbarMessageType.ERROR -> ErrorColor
        }

        Snackbar(
            snackbarData = snackbarData,
            containerColor = backgroundColor
        )
    }
}
