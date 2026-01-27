package com.kuzmin.flowersoflife.feature.home.ui.screen.children.viewmodel

import androidx.lifecycle.viewModelScope
import com.kuzmin.flowersoflife.core.domain.model.aggregate.ChildDashboard
import com.kuzmin.flowersoflife.core.local.event_bus.SharedFlowMap
import com.kuzmin.flowersoflife.core.navigation.NavigationManager
import com.kuzmin.flowersoflife.core.navigation.model.NavigationCommand
import com.kuzmin.flowersoflife.core.navigation.routing.Destination
import com.kuzmin.flowersoflife.core.ui.event.UiEvent
import com.kuzmin.flowersoflife.feature.api.usecases.home.GetChildrenDashboardUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.local.GetFamilyFromLocalUseCase
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.state.BaseChildrenListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeChildrenDashboardViewModel(
    private val getChildrenDashboardUseCase: GetChildrenDashboardUseCase,
    private val getFamilyFromLocalUseCase: GetFamilyFromLocalUseCase,
    private val navigationManager: NavigationManager,
    sharedFlowMap: SharedFlowMap<UiEvent>
) : BaseChildrenListViewModel<ChildDashboard, BaseChildrenListState<ChildDashboard>>(sharedFlowMap) {

    init {
        fetchChildrenDashboard()
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
    override val _state: MutableStateFlow<BaseChildrenListState<ChildDashboard>> =
        MutableStateFlow(BaseChildrenListState.Loading)
    override val state: StateFlow<BaseChildrenListState<ChildDashboard>> = _state.asStateFlow()

    fun onChildClick(childId: String) {
        viewModelScope.launch {
            navigationManager.navigate(
                NavigationCommand.ToDestination(
                    destination = Destination.PARENT_CHILD_DASHBOARD,
                    args = listOf(childId)
                )
            )
        }
    }

    override fun handleException(throwable: Throwable) {
        TODO("Not yet implemented")
    }

    override fun getChildrenList(state: BaseChildrenListState<ChildDashboard>): List<ChildDashboard>? {
        return (state as? BaseChildrenListState.Success)?.children
    }

    override fun updateChildrenList(
        state: BaseChildrenListState<ChildDashboard>,
        newList: List<ChildDashboard>
    ): BaseChildrenListState<ChildDashboard> {
        return if (state is BaseChildrenListState.Success) {
            state.copy(children = newList)
        } else {
            state
        }
    }
}