package com.kuzmin.flowersoflife.common.model

data class AppUiData(
    val title: String = "",
    val profilePhotoUrl: String? = null,
    val isBackVisible: Boolean = true,
    val isProfilePhotoVisible: Boolean = false,
    val isSettingsVisible: Boolean = false,
    val isHamburgerVisible: Boolean = true
)
