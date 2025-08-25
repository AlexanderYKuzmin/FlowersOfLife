package com.kuzmin.flowersoflife.extensions

import com.kuzmin.flowersoflife.common.model.AppUiData
import com.kuzmin.flowersoflife.core.local.resource_provider.ResourceProvider
import com.kuzmin.flowersoflife.ui.state.AppUiState

fun AppUiState.Success.updateWith(
    appUiData: AppUiData,
    resourceProvider: ResourceProvider
): AppUiState.Success {
    return copy(appUiData = appUiData)
}
