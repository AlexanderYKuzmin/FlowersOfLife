package com.kuzmin.flowersoflife.feature.home.ui.screen.children.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.kuzmin.flowersoflife.common.R
import com.kuzmin.flowersoflife.common.model.TopBarUiSettings
import com.kuzmin.flowersoflife.core.domain.model.aggregate.ChildDashboard
import com.kuzmin.flowersoflife.core.domain.usecases.dashboard.CalculateChartDataUseCase
import com.kuzmin.flowersoflife.core.local.event_bus.SharedFlowMap
import com.kuzmin.flowersoflife.core.local.resource_provider.ResourceProvider
import com.kuzmin.flowersoflife.core.navigation.routing.DestinationArgs
import com.kuzmin.flowersoflife.core.ui.event.UiEvent
import com.kuzmin.flowersoflife.feature.api.usecases.home.GetChildDashboardRemoteUseCase
import com.kuzmin.flowersoflife.feature.home.ui.model.ChartDashboardGraphicsData
import com.kuzmin.flowersoflife.feature.home.ui.model.ChartPeriod
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.state.BaseChildState
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.viewmodel.contract.OnChartAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChildDashboardDetailsViewModel(
    private val getChildDashboardRemoteUseCase: GetChildDashboardRemoteUseCase,
    private val calculateChartDataUseCase: CalculateChartDataUseCase,
    savedStateHandle: SavedStateHandle,
    sharedFlowMap: SharedFlowMap<UiEvent>,
    private val resourceProvider: ResourceProvider
) : BaseChildViewModel<ChildDashboard, BaseChildState<ChildDashboard>>(sharedFlowMap) {

    private val _chartData = MutableStateFlow<ChartDashboardGraphicsData?>(null)
    val chartData: StateFlow<ChartDashboardGraphicsData?> = _chartData.asStateFlow()

    private var currentPeriod = ChartPeriod.MONTH
    private var weekIndex = 0
    private var monthIndex = 0
    private var yearIndex = 0

    override val _state: MutableStateFlow<BaseChildState<ChildDashboard>> =
        MutableStateFlow(BaseChildState.Loading)
    override val state: StateFlow<BaseChildState<ChildDashboard>>
        get() = _state.asStateFlow()

    init {
        val childId: String? = savedStateHandle[DestinationArgs.CHILD_ID]

        if (childId != null) {
            fetchChildDashboard(childId)
        } else {
            _state.value = BaseChildState.Error(
                resourceProvider.getString(R.string.error_child_id_is_null)
            )
        }

        updateAppState()
    }

    val onChartAction = object : OnChartAction {
        override fun onChartPeriodChange(period: ChartPeriod) =
            handlePeriodChange(period)
        override fun onChartNavigateNext() =
            handleNavigateNext()
        override fun onChartNavigatePrevious() =
            handleNavigatePrevious()
    }

    private fun fetchChildDashboard(childId: String) {
        viewModelScope.launch(ioContext) {
            val childDashboard = getChildDashboardRemoteUseCase(childId)

            if (childDashboard != null) {
                _state.value = BaseChildState.Success(childDashboard)
                calculateChartData()
            } else {
                _state.value = BaseChildState.Error(
                    resourceProvider.getString(R.string.error_child_id_is_null)
                )
            }
        }
    }

    private fun calculateChartData() {
        doIfSuccess { dashboard ->
            _chartData.value = calculateChartDataUseCase(
                dashboard = dashboard,
                period = currentPeriod,
                periodIndex = when (currentPeriod) {
                    ChartPeriod.WEEK -> weekIndex
                    ChartPeriod.MONTH -> monthIndex
                    ChartPeriod.YEAR -> yearIndex
                },
                weekIndex = weekIndex,
                monthIndex = monthIndex,
                yearIndex = yearIndex
            )
        }
    }

    private fun handlePeriodChange(period: ChartPeriod) {
        if (currentPeriod != period) {
            currentPeriod = period
            calculateChartData()
        }
    }

    private fun handleNavigateNext() {
        when (currentPeriod) {
            ChartPeriod.WEEK -> weekIndex++
            ChartPeriod.MONTH -> monthIndex++
            ChartPeriod.YEAR -> yearIndex++
        }
        calculateChartData()
    }

    private fun handleNavigatePrevious() {
        when (currentPeriod) {
            ChartPeriod.WEEK -> weekIndex--
            ChartPeriod.MONTH -> monthIndex--
            ChartPeriod.YEAR -> yearIndex--
        }
        calculateChartData()
    }

    private fun updateAppState() {
        viewModelScope.launch {
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

    override fun handleException(throwable: Throwable) {
        TODO("Not yet implemented")
    }

    override fun getSuccessData(state: BaseChildState<ChildDashboard>): ChildDashboard? {
        return (state as? BaseChildState.Success<ChildDashboard>)?.data
    }

    override fun createSuccessState(data: ChildDashboard): BaseChildState<ChildDashboard> {
        TODO("Not yet implemented")
    }
}