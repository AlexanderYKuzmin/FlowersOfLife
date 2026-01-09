package com.kuzmin.flowersoflife.core.ui.components.snackbarhost

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import com.kuzmin.flowersoflife.core.ui.components.snackbar.SnackbarMessageType
import com.kuzmin.flowersoflife.core.ui.components.snackbar.TypedSnackbarVisuals
import com.kuzmin.flowersoflife.core.ui.theme.KabTheme

@Composable
fun CustomSnackbarHost(
    hostState: SnackbarHostState
) {
    SnackbarHost(hostState = hostState) { snackbarData ->
        val type = (snackbarData.visuals as? TypedSnackbarVisuals)?.type ?: SnackbarMessageType.INFO

        val backgroundColor = when (type) {
            SnackbarMessageType.INFO -> KabTheme.colors.infoText
            SnackbarMessageType.WARNING -> KabTheme.colors.warningText
            SnackbarMessageType.ERROR -> KabTheme.colors.errorText
        }

        Snackbar(
            snackbarData = snackbarData,
            containerColor = backgroundColor
        )
    }
}
