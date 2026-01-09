package com.kuzmin.flowersoflife.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kuzmin.flowersoflife.R
import com.kuzmin.flowersoflife.common.model.TabBarUiSettings
import com.kuzmin.flowersoflife.core.ui.theme.Bold22
import com.kuzmin.flowersoflife.core.ui.theme.KabTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenTopBar(
    tabbarUiSettings: TabBarUiSettings = TabBarUiSettings(),
    onNavigationIconClick: () -> Unit,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = tabbarUiSettings.title,
                style = Bold22,
                color = KabTheme.colors.primaryText
            )
        },
        navigationIcon = {
            if (tabbarUiSettings.isHamburgerVisible) {
                IconButton(onClick = onNavigationIconClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_menu_24),
                        contentDescription = stringResource(id = R.string.menu_icon_description),
                        tint = Color.Unspecified
                    )
                }
            } else if (tabbarUiSettings.isBackVisible) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(id = com.google.android.material.R.drawable.material_ic_keyboard_arrow_left_black_24dp),
                        contentDescription = stringResource(id = R.string.back_icon_description),
                        tint = Color.Unspecified
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = KabTheme.colors.primary,
            titleContentColor = KabTheme.colors.primaryText,
            navigationIconContentColor = KabTheme.colors.primaryText
        )
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    name = "MainScreenTopBar Preview"
)
@Composable
private fun MainScreenTopBarPreview() {
    KabTheme {
        MainScreenTopBar(
            tabbarUiSettings = TabBarUiSettings(),
            onNavigationIconClick = {},
            onBackClick = {}
        )
    }
}