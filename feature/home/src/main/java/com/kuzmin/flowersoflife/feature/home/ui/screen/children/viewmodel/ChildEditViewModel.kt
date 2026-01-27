package com.kuzmin.flowersoflife.feature.home.ui.screen.children.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.kuzmin.flowersoflife.common.R
import com.kuzmin.flowersoflife.common.model.TopBarUiSettings
import com.kuzmin.flowersoflife.core.local.event_bus.FlowKey
import com.kuzmin.flowersoflife.core.local.event_bus.SharedFlowMap
import com.kuzmin.flowersoflife.core.local.resource_provider.ResourceProvider
import com.kuzmin.flowersoflife.core.navigation.NavigationManager
import com.kuzmin.flowersoflife.core.navigation.model.NavigationCommand
import com.kuzmin.flowersoflife.core.navigation.routing.DestinationArgs
import com.kuzmin.flowersoflife.core.ui.event.UiEvent
import com.kuzmin.flowersoflife.feature.api.usecases.home.CreateChildRemoteUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.home.GetChildDashboardRemoteUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.home.UpdateChildRemoteUseCase
import com.kuzmin.flowersoflife.feature.home.domain.event.ChildEvent
import com.kuzmin.flowersoflife.feature.home.domain.mapper.toChild
import com.kuzmin.flowersoflife.feature.home.domain.mapper.toChildUi
import com.kuzmin.flowersoflife.feature.home.exception.error.ChildEditErrorType
import com.kuzmin.flowersoflife.feature.home.ui.model.ChildUi
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.state.BaseChildState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChildEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val navigationManager: NavigationManager,
    private val updateChildUseCase: UpdateChildRemoteUseCase,
    private val createChildRemoteUseCase: CreateChildRemoteUseCase,
    private val getChildDashboardRemoteUseCase: GetChildDashboardRemoteUseCase,
    private val childEventFlowMap: SharedFlowMap<ChildEvent>,
    sharedFlowMap: SharedFlowMap<UiEvent>,
    private val resourceProvider: ResourceProvider
) : BaseChildViewModel<ChildUi, BaseChildState<ChildUi>>(sharedFlowMap) {

    override val _state: MutableStateFlow<BaseChildState<ChildUi>> =
        MutableStateFlow(BaseChildState.Loading)

    override val state: StateFlow<BaseChildState<ChildUi>> = _state.asStateFlow()
    private val _errors = MutableStateFlow<Set<ChildEditErrorType>>(emptySet())
    val errors = _errors.asStateFlow()

    init {
        val childId: String? = savedStateHandle[DestinationArgs.CHILD_ID]

        if (childId != null) {
            fetchChild(childId)
        } else {
            _state.value = BaseChildState.Success(
                data = ChildUi()
            )
        }

        viewModelScope.launch { //TODO перенести в отдельный private method
            updateAppState(
                topBarUiSettings = TopBarUiSettings(
                    title = resourceProvider.getString(R.string.child_title),
                    isBackVisible = true,
                    isHamburgerVisible = false
                ),
                isBottomNavVisible = false
            )
        }
    }

    private fun fetchChild(childId: String) {
        viewModelScope.launch(ioContext) {
            _state.value = BaseChildState.Loading

            val dashBoard = getChildDashboardRemoteUseCase(childId)

            _state.value = BaseChildState.Success(
                data = dashBoard?.toChildUi() ?: ChildUi()
            )
        }
    }

    fun onChildChange(block: ChildUi.() -> ChildUi) {
        updateIfSuccess {
            block(it)
        }
    }

    fun onAddPhotoClick() {

    }

    fun onSaveClick() {
        viewModelScope.launch(ioContext) {
            val childUi = getIfSuccess {
                getSuccessData(state.value)
            } ?: return@launch

            createChildRemoteUseCase(
                child = childUi.toChild()
            )

            childEventFlowMap.emit(FlowKey.CHILD_EVENT, ChildEvent.Created)

            navigationManager.navigate(
                NavigationCommand.Back()
            )
        }
    }

    fun onCancelClick() {

    }

    override fun getSuccessData(state: BaseChildState<ChildUi>): ChildUi? {
        return when (state) {
            is BaseChildState.Success -> state.data
            else -> null
        }
    }

    override fun createSuccessState(data: ChildUi): BaseChildState<ChildUi> {
        return BaseChildState.Success(data = data)
    }

    override fun handleException(throwable: Throwable) {
        TODO("Not yet implemented")
    }
}