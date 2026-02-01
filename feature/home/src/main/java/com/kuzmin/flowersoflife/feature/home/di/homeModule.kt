package com.kuzmin.flowersoflife.feature.home.di

import com.kuzmin.flowersoflife.core.domain.usecases.dashboard.CalculateChartDataUseCase
import com.kuzmin.flowersoflife.core.local.event_bus.FlowKey.CHILD_EVENT
import com.kuzmin.flowersoflife.core.local.event_bus.FlowKey.UI_EVENT
import com.kuzmin.flowersoflife.feature.api.usecases.home.CreateChildRemoteUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.home.DeleteChildRemoteUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.home.GetChildDashboardRemoteUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.home.GetChildrenDashboardUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.home.UpdateChildRemoteUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.local.GetFamilyFromLocalUseCase
import com.kuzmin.flowersoflife.feature.home.domain.usecases.CreateChildRemoteUseCaseImpl
import com.kuzmin.flowersoflife.feature.home.domain.usecases.DeleteChildRemoteUseCaseImpl
import com.kuzmin.flowersoflife.feature.home.domain.usecases.GetChildDashboardRemoteUseCaseImpl
import com.kuzmin.flowersoflife.feature.home.domain.usecases.GetChildrenDashboardUseCaseImpl
import com.kuzmin.flowersoflife.feature.home.domain.usecases.GetFamilyFromLocalUseCaseImpl
import com.kuzmin.flowersoflife.feature.home.domain.usecases.UpdateChildRemoteUseCaseImpl
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.viewmodel.ChildDashboardDetailsViewModel
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.viewmodel.ChildEditViewModel
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.viewmodel.ChildrenViewModel
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.viewmodel.HomeChildrenDashboardViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val homeModule = module {
    single<GetFamilyFromLocalUseCase> { GetFamilyFromLocalUseCaseImpl(get()) }
    single<GetChildrenDashboardUseCase> { GetChildrenDashboardUseCaseImpl(get()) }
    single<CreateChildRemoteUseCase> { CreateChildRemoteUseCaseImpl(get()) }
    single<UpdateChildRemoteUseCase> { UpdateChildRemoteUseCaseImpl(get()) }
    single<DeleteChildRemoteUseCase> { DeleteChildRemoteUseCaseImpl(get()) }
    single<GetChildDashboardRemoteUseCase> { GetChildDashboardRemoteUseCaseImpl(get()) }
    single<CalculateChartDataUseCase> { CalculateChartDataUseCase() }

    viewModel {
        ChildrenViewModel(
            getFamilyFromLocalUseCase = get(),
            getChildrenDashboardUseCase = get(),
            deleteChildRemoteUseCase = get(),
            navigationManager = get(),
            resourceProvider = get(),
            sharedFlowMap = get(named(UI_EVENT)),
            childEventFlowMap = get(named(CHILD_EVENT)),
        )
    }

    viewModel {
        ChildEditViewModel(
            savedStateHandle = get(),
            createChildRemoteUseCase = get(),
            getChildDashboardRemoteUseCase = get(),
            updateChildUseCase = get(),
            navigationManager = get(),
            resourceProvider = get(),
            sharedFlowMap = get(named(UI_EVENT)),
            childEventFlowMap = get(named(CHILD_EVENT))
        )
    }

    viewModel {
        HomeChildrenDashboardViewModel(
            getChildrenDashboardUseCase = get(),
            getFamilyFromLocalUseCase = get(),
            navigationManager = get(),
            sharedFlowMap = get(named(UI_EVENT)),
            resourceProvider = get(),
        )
    }

    viewModel {
        ChildDashboardDetailsViewModel(
            savedStateHandle = get(),
            getChildDashboardRemoteUseCase = get(),
            calculateChartDataUseCase = get(),
            sharedFlowMap = get(named(UI_EVENT)),
            resourceProvider = get()
        )
    }
}