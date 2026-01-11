package com.kuzmin.flowersoflife.extensions

import com.kuzmin.flowersoflife.common.model.TopBarUiSettings
import com.kuzmin.flowersoflife.core.local.resource_provider.ResourceProvider
import com.kuzmin.flowersoflife.ui.state.AppUiState

fun AppUiState.Success.updateWith(
    tabbarUiSettings: TopBarUiSettings,
    resourceProvider: ResourceProvider
): AppUiState.Success {
    return copy(topBarUiSettings = tabbarUiSettings)
}
