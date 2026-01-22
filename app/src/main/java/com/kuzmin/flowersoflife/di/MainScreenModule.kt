package com.kuzmin.flowersoflife.di

import com.kuzmin.flowersoflife.core.local.event_bus.FlowKey.UI_EVENT
import com.kuzmin.flowersoflife.ui.viewmodels.MainScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mainScreenModule = module {
    viewModel {
        MainScreenViewModel(
            checkAuthUseCase = get(),
            getUserFromFbUseCase = get(),
            getUserFamilyFromRemoteUseCase = get(),
            saveUserFamilyToLocalUseCase = get(),
            resourceProvider = get(),
            sharedFlowMap = get(named(UI_EVENT)),
            navigationManager = get()
        )
    }
}