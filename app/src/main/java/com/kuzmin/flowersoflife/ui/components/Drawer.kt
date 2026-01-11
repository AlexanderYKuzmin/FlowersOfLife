package com.kuzmin.flowersoflife.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kuzmin.flowersoflife.R
import com.kuzmin.flowersoflife.core.domain.model.UserRole
import com.kuzmin.flowersoflife.core.domain.model.aggregate.UserFamily
import com.kuzmin.flowersoflife.core.ui.theme.Bold20
import com.kuzmin.flowersoflife.core.ui.theme.KabTheme
import com.kuzmin.flowersoflife.core.ui.theme.Medium16
import com.kuzmin.flowersoflife.core.ui.theme.Regular14

@Composable
fun DrawerContent(
    userFamily: UserFamily? = null,
    modifier: Modifier = Modifier,
    onActionClick: (DrawerAction) -> Unit = {},
    onSettingClick: (DrawerSetting) -> Unit = {}
) {
    val familyName = userFamily?.family?.familyName ?: ""
    val userName = userFamily?.user?.name ?: ""
    val userRole = userFamily?.user?.role ?: UserRole.PARENT

    Column(
        modifier = modifier
            .background(KabTheme.colors.surface)
            .padding(vertical = 16.dp)
    ) {
        // Header section
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Text(
                text = familyName.ifEmpty { stringResource(id = R.string.family) },
                style = Bold20,
                color = KabTheme.colors.primaryText
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = userName,
                style = Medium16,
                color = KabTheme.colors.primaryText
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = when (userRole) {
                    UserRole.PARENT -> stringResource(id = R.string.parent)
                    UserRole.CHILD -> stringResource(id = R.string.child)
                },
                style = Regular14,
                color = KabTheme.colors.primaryDimmedText
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        DrawerAction.entries.forEach { action ->
            NavigationDrawerItem(
                icon = {
                    Icon(
                        painter = painterResource(id = action.iconResId),
                        contentDescription = stringResource(id = action.titleResId)
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = action.titleResId),
                        style = Medium16
                    )
                },
                selected = false,
                onClick = { onActionClick(action) },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                colors = NavigationDrawerItemDefaults.colors(
                    selectedIconColor = KabTheme.colors.primaryText,
                    selectedTextColor = KabTheme.colors.primaryText,
                    unselectedIconColor = KabTheme.colors.primaryText,
                    unselectedTextColor = KabTheme.colors.primaryText
                )
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = KabTheme.colors.grayLight
        )

        DrawerSetting.entries.forEach { setting ->
            NavigationDrawerItem(
                icon = {
                    Icon(
                        painter = painterResource(id = setting.iconResId),
                        contentDescription = stringResource(id = setting.titleResId)
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = setting.titleResId),
                        style = Medium16
                    )
                },
                selected = false,
                onClick = { onSettingClick(setting) },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                colors = NavigationDrawerItemDefaults.colors(
                    selectedIconColor = KabTheme.colors.primaryText,
                    selectedTextColor = KabTheme.colors.primaryText,
                    unselectedIconColor = KabTheme.colors.primaryText,
                    unselectedTextColor = KabTheme.colors.primaryText
                )
            )
        }
    }
}


enum class DrawerAction(
    val titleResId: Int,
    val iconResId: Int
) {
    ACTIONS(R.string.actions, R.drawable.ic_tasks),
    GOALS(R.string.goals, R.drawable.ic_count),
    CHILDREN(R.string.children, R.drawable.ic_home),
    ADULTS(R.string.adults, R.drawable.ic_home),
    REGISTRATION(R.string.registration, R.drawable.ic_notifications),
    LOGOUT(R.string.logout, R.drawable.outline_arrow_back_24)
}

enum class DrawerSetting(
    val titleResId: Int,
    val iconResId: Int
) {
    SETTINGS(R.string.settings, R.drawable.baseline_menu_24),
    PROFILE(R.string.profile, R.drawable.ic_home),
    HELP(R.string.help, R.drawable.ic_notifications),
    ABOUT(R.string.about, R.drawable.baseline_menu_24)
}