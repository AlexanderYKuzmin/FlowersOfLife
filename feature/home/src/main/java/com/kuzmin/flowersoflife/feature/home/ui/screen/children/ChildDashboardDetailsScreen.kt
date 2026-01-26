package com.kuzmin.flowersoflife.feature.home.ui.screen.children

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.kuzmin.flowersoflife.core.ui.theme.KabTheme

@Composable
fun ChildDashboardDetailsScreen(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        KabTheme.colors.primary,
                        KabTheme.colors.primaryDimmedText,
                    )
                )
            )
            .fillMaxSize()
    ) {
    }

}