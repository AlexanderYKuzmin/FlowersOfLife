package com.kuzmin.flowersoflife.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kuzmin.flowersoflife.core.ui.theme.KabTheme
import com.kuzmin.flowersoflife.domain.model.BottomNavItem

@Composable
fun ParentBottomNavigationBar(
    navController: NavHostController
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Tasks,
        BottomNavItem.Finance,
        BottomNavItem.Rewards
    )

    NavigationBar(
        containerColor = KabTheme.colors.primary,
        contentColor = KabTheme.colors.primaryText,
    ) {
        val currentRoute = currentRoute(navController)
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconResId),
                        contentDescription = stringResource(id = item.titleRes),
                    )
                },
                label = { Text(stringResource(id = item.titleRes)) },
                selected = currentRoute == item.route,
                colors = NavigationBarItemColors(
                    selectedIconColor = KabTheme.colors.primaryText,
                    selectedTextColor = KabTheme.colors.primaryText,
                    selectedIndicatorColor = KabTheme.colors.primary.copy(alpha = 0.4f),
                    unselectedIconColor = KabTheme.colors.grayLight,
                    unselectedTextColor = KabTheme.colors.grayLight,
                    disabledIconColor = KabTheme.colors.grayLight,
                    disabledTextColor = KabTheme.colors.grayLight,
                ),
                onClick = {
                    /*navController.navigate(detailedRoute) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }*/
                }
            )
        }
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
