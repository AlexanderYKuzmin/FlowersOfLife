package com.kuzmin.flowersoflife.feature.home.ui.screen.children.viewmodel

import androidx.lifecycle.viewModelScope
import com.kuzmin.flowersoflife.common.R
import com.kuzmin.flowersoflife.common.model.TopBarUiSettings
import com.kuzmin.flowersoflife.core.domain.model.aggregate.ChildDashboard
import com.kuzmin.flowersoflife.core.local.event_bus.SharedFlowMap
import com.kuzmin.flowersoflife.core.local.resource_provider.ResourceProvider
import com.kuzmin.flowersoflife.core.navigation.NavigationManager
import com.kuzmin.flowersoflife.core.navigation.model.NavigationCommand
import com.kuzmin.flowersoflife.core.navigation.routing.Destination
import com.kuzmin.flowersoflife.core.navigation.routing.DestinationArgs
import com.kuzmin.flowersoflife.core.ui.event.UiEvent
import com.kuzmin.flowersoflife.feature.api.usecases.home.GetChildrenDashboardUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.local.GetFamilyFromLocalUseCase
import com.kuzmin.flowersoflife.feature.home.domain.mapper.toChild
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.state.BaseChildrenListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChildrenListViewModel(
    private val getFamilyFromLocalUseCase: GetFamilyFromLocalUseCase,
    private val getChildrenDashboardUseCase: GetChildrenDashboardUseCase,
    private val navigationManager: NavigationManager,
    sharedFlowMap: SharedFlowMap<UiEvent>,
    private val resourceProvider: ResourceProvider,
) : BaseChildrenListviewModel<ChildDashboard, BaseChildrenListState<ChildDashboard>>(sharedFlowMap) {

    override val _state: MutableStateFlow<BaseChildrenListState<ChildDashboard>> =
        MutableStateFlow(BaseChildrenListState.Loading)
    override val state: StateFlow<BaseChildrenListState<ChildDashboard>> = _state.asStateFlow()

    init {
        fetchChildrenDashboard()

        initAppState()
    }

    private fun fetchChildrenDashboard() {
        viewModelScope.launch(ioContext) {
            _state.value = BaseChildrenListState.Loading

            val family = getFamilyFromLocalUseCase()

            val childrenList = getChildrenDashboardUseCase(family.familyId)

            _state.value = BaseChildrenListState.Success(
                children = childrenList
            )
        }
    }

    fun onChildClick(childDashboard: ChildDashboard) {
        viewModelScope.launch {
            navigationManager.navigate(
                NavigationCommand.ToDestinationParcelable(
                    destination = Destination.PARENT_EDIT_CHILD,
                    parcelableArgs = mapOf(DestinationArgs.CHILD to childDashboard.toChild())
                )
            )
        }
    }

    fun onBackPressed() {

    }

    override fun handleException(throwable: Throwable) {
        TODO("Not yet implemented")
    }

    override fun getSuccessData(state: BaseChildrenListState<ChildDashboard>): List<ChildDashboard>? {
        return when (state) {
            is BaseChildrenListState.Success -> state.children
            else -> null
        }
    }

    override fun createSuccessState(data: List<ChildDashboard>): BaseChildrenListState<ChildDashboard> {
        return BaseChildrenListState.Success(children = data)
    }

    private fun initAppState() {
        viewModelScope.launch {
            updateAppState(
                topBarUiSettings = TopBarUiSettings(
                    title = resourceProvider.getString(R.string.children_title),
                    isBackVisible = false,
                    isHamburgerVisible = true,
                ),
                isBottomNavVisible = true
            )
        }
    }
}