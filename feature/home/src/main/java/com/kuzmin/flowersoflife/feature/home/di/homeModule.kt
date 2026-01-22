package com.kuzmin.flowersoflife.feature.home.di

import com.kuzmin.flowersoflife.feature.api.usecases.home.GetChildrenDashboardUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.local.GetFamilyFromLocalUseCase
import com.kuzmin.flowersoflife.feature.home.domain.usecases.GetChildrenDashboardUseCaseImpl
import com.kuzmin.flowersoflife.feature.home.domain.usecases.GetFamilyFromLocalUseCaseImpl
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.ChildrenListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    single<GetFamilyFromLocalUseCase> { GetFamilyFromLocalUseCaseImpl(get()) }
    single<GetChildrenDashboardUseCase> { GetChildrenDashboardUseCaseImpl(get()) }

    viewModel {
        ChildrenListViewModel(
            getFamilyFromLocalUseCase = get(),
            getChildrenDashboardUseCase = get(),
            navigationManager = get()
        )
    }
}