package com.kuzmin.flowersoflife.feature.home.ui.screen.children.viewmodel

import androidx.lifecycle.viewModelScope
import com.kuzmin.flowersoflife.common.R
import com.kuzmin.flowersoflife.common.model.TopBarUiSettings
import com.kuzmin.flowersoflife.core.domain.model.aggregate.ChildDashboard
import com.kuzmin.flowersoflife.core.local.event_bus.FlowKey
import com.kuzmin.flowersoflife.core.local.event_bus.SharedFlowMap
import com.kuzmin.flowersoflife.core.local.resource_provider.ResourceProvider
import com.kuzmin.flowersoflife.core.navigation.NavigationManager
import com.kuzmin.flowersoflife.core.navigation.model.NavigationCommand
import com.kuzmin.flowersoflife.core.navigation.routing.Destination
import com.kuzmin.flowersoflife.core.ui.event.UiEvent
import com.kuzmin.flowersoflife.feature.api.usecases.home.DeleteChildRemoteUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.home.GetChildrenDashboardUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.local.GetFamilyFromLocalUseCase
import com.kuzmin.flowersoflife.feature.home.domain.event.ChildEvent
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.state.BaseChildrenListState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class ChildrenViewModel(
    private val getFamilyFromLocalUseCase: GetFamilyFromLocalUseCase,
    private val getChildrenDashboardUseCase: GetChildrenDashboardUseCase,
    private val deleteChildRemoteUseCase: DeleteChildRemoteUseCase,
    private val navigationManager: NavigationManager,
    private val childEventFlowMap: SharedFlowMap<ChildEvent>,
    sharedFlowMap: SharedFlowMap<UiEvent>,
    private val resourceProvider: ResourceProvider,
) : BaseChildrenListViewModel<ChildDashboard, BaseChildrenListState<ChildDashboard>>(sharedFlowMap) {

    override val _state: MutableStateFlow<BaseChildrenListState<ChildDashboard>> =
        MutableStateFlow(BaseChildrenListState.Loading)
    override val state: StateFlow<BaseChildrenListState<ChildDashboard>> = _state.asStateFlow()

    init {
        fetchChildrenDashboard()
        observeChildEvents()
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

    fun onChildClick(childId: String?) {
        viewModelScope.launch {
            navigationManager.navigate(
                NavigationCommand.ToDestination(
                    destination = Destination.PARENT_EDIT_CHILD,
                    args = listOf(childId ?: "")
                )
            )
        }
    }

    fun onRemoveChild(childDashboard: ChildDashboard) {
        viewModelScope.launch {
            delay(300)
            updateIfSuccess<BaseChildrenListState.Success<ChildDashboard>> { state ->
                state.copy(
                    pendingRemovalChild = childDashboard
                )
            }
        }
    }

    fun onRemoveChildApproved(childDashboard: ChildDashboard) {
        viewModelScope.launch(ioContext) {
            deleteChildRemoteUseCase(childDashboard.user.userId)
            fetchChildrenDashboard()
        }
    }

    fun refresh() {
        fetchChildrenDashboard()
    }

    fun onBackPressed() {

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

    fun initAppState() {
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

    private fun observeChildEvents() {
        viewModelScope.launch {
            childEventFlowMap.observe(FlowKey.CHILD_EVENT)
                .filterNotNull()
                .collect { event ->
                    when(event) {
                        is ChildEvent.Created,
                        is ChildEvent.Updated -> {
                            fetchChildrenDashboard()
                        }
                        else -> Unit
                    }
                }
        }
    }
}