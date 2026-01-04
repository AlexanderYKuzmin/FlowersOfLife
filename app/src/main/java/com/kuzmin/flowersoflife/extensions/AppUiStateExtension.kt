package com.kuzmin.flowersoflife.extensions

import com.kuzmin.flowersoflife.common.model.TabBarUiSettings
import com.kuzmin.flowersoflife.core.local.resource_provider.ResourceProvider
import com.kuzmin.flowersoflife.ui.state.AppUiState

fun AppUiState.Success.updateWith(
    tabbarUiSettings: TabBarUiSettings,
    resourceProvider: ResourceProvider
): AppUiState.Success {
    return copy(tabbarUiSettings = tabbarUiSettings)
}
