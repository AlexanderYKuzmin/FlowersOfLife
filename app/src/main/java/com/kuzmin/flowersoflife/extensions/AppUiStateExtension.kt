package com.kuzmin.flowersoflife.extensions

import com.kuzmin.flowersoflife.R
import com.kuzmin.flowersoflife.common.model.AppData
import com.kuzmin.flowersoflife.core.local.resource_provider.ResourceProvider
import com.kuzmin.flowersoflife.ui.state.AppUiState

fun AppUiState.Success.updateWith(
    appData: AppData,
    resourceProvider: ResourceProvider
): AppUiState.Success {
    return copy(
        title = appData.title ?: resourceProvider.getString(R.string.app_name),
        profilePhotoUrl = appData.profilePhotoUrl ?: profilePhotoUrl,
        showBackButton = appData.showBackButton ?: false,
        showProfilePhoto = appData.showProfilePhoto ?: showProfilePhoto,
        showSettingsButton = appData.showSettingsButton ?: showSettingsButton
    )
}
