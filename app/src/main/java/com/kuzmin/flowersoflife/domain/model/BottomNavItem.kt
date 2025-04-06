package com.kuzmin.flowersoflife.domain.model

import androidx.annotation.DrawableRes
import com.kuzmin.flowersoflife.R
import com.kuzmin.flowersoflife.common.constants.Destination.FINANCE
import com.kuzmin.flowersoflife.common.constants.Destination.HOME
import com.kuzmin.flowersoflife.common.constants.Destination.NOTIFICATIONS
import com.kuzmin.flowersoflife.common.constants.Destination.TASKS

sealed class BottomNavItem(
    val route: String,
    @DrawableRes val iconResId: Int,
    val titleRes: Int
) {
    data object Home : BottomNavItem(HOME, R.drawable.ic_home, R.string.home)
    data object Tasks : BottomNavItem(TASKS, R.drawable.ic_tasks, R.string.tasks)
    data object Finance : BottomNavItem(FINANCE, R.drawable.ic_count, R.string.finance)
    data object Rewards : BottomNavItem(NOTIFICATIONS, R.drawable.ic_notifications, R.string.notifications)
}