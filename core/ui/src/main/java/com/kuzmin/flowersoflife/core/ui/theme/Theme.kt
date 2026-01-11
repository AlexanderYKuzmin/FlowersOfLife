package com.kuzmin.flowersoflife.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

private val LocalColors = compositionLocalOf<KabColorScheme> {
    error("No colors provided! Make sure to wrap all usages of LxpDriverColors components in Theme.")
}

@Composable
fun KabTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colors: KabColorScheme = if (darkTheme) {
        KabColorScheme.defaultDarkColors()
    } else {
        KabColorScheme.defaultLightColors()
    },
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColors provides colors
    ) {
        content()
    }
}

object KabTheme {
    val colors: KabColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current
}