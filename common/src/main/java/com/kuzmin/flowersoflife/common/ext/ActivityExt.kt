package com.kuzmin.flowersoflife.common.ext

import android.app.Activity
import android.os.Build
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat

fun Activity.setSystemBarsAppearance(
    statusBarColor: Color = Color.Transparent,
    navigationBarColor: Color = Color.Transparent
) {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.statusBarColor = statusBarColor.toArgb()
    window.navigationBarColor = navigationBarColor.toArgb()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.setDecorFitsSystemWindows(false)
    }
}