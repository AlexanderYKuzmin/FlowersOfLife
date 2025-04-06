package com.kuzmin.flowersoflife.common.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun Int.toPx(): Float {
    val density = LocalDensity.current
    return this * density.density
}


@Composable
fun Dp.toPx(): Float {
    val density = LocalDensity.current
    return this.value * density.density
}