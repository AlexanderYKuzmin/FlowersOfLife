package com.kuzmin.flowersoflife.feature.home.di

import com.kuzmin.flowersoflife.core.local.event_bus.FlowKey.CHILD_EVENT
import com.kuzmin.flowersoflife.core.local.event_bus.FlowKey.UI_EVENT
import com.kuzmin.flowersoflife.feature.api.usecases.home.CreateChildRemoteUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.home.DeleteChildRemoteUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.home.GetChildrenDashboardUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.home.UpdateChildRemoteUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.local.GetFamilyFromLocalUseCase
import com.kuzmin.flowersoflife.feature.home.domain.usecases.CreateChildRemoteUseCaseImpl
import com.kuzmin.flowersoflife.feature.home.domain.usecases.DeleteChildRemoteUseCaseImpl
import com.kuzmin.flowersoflife.feature.home.domain.usecases.GetChildrenDashboardUseCaseImpl
import com.kuzmin.flowersoflife.feature.home.domain.usecases.GetFamilyFromLocalUseCaseImpl
import com.kuzmin.flowersoflife.feature.home.domain.usecases.UpdateChildRemoteUseCaseImpl
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.viewmodel.ChildEditViewModel
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.viewmodel.ChildrenListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val homeModule = module {
    single<GetFamilyFromLocalUseCase> { GetFamilyFromLocalUseCaseImpl(get()) }
    single<GetChildrenDashboardUseCase> { GetChildrenDashboardUseCaseImpl(get()) }
    single<CreateChildRemoteUseCase> { CreateChildRemoteUseCaseImpl(get()) }
    single<UpdateChildRemoteUseCase> { UpdateChildRemoteUseCaseImpl(get()) }
    single<DeleteChildRemoteUseCase> { DeleteChildRemoteUseCaseImpl(get()) }

    viewModel {
        ChildrenListViewModel(
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
            getUserFromRemoteUseCase = get(),
            createChildRemoteUseCase = get(),
            updateChildUseCase = get(),
            navigationManager = get(),
            resourceProvider = get(),
            sharedFlowMap = get(named(UI_EVENT)),
            childEventFlowMap = get(named(CHILD_EVENT))
        )
    }
}