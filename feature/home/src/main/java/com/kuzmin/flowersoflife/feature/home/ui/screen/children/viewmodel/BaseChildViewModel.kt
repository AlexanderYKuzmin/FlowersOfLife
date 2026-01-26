package com.kuzmin.flowersoflife.feature.home.ui.screen.children.viewmodel

import androidx.lifecycle.ViewModel
import com.kuzmin.flowersoflife.common.model.TopBarUiSettings
import com.kuzmin.flowersoflife.core.local.event_bus.FlowKey.UI_EVENT
import com.kuzmin.flowersoflife.core.local.event_bus.SharedFlowMap
import com.kuzmin.flowersoflife.core.ui.components.snackbar.SnackbarMessageType
import com.kuzmin.flowersoflife.core.ui.event.UiEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseChildViewModel<UI, STATE>(
    private val sharedFlowMap: SharedFlowMap<UiEvent>
) : ViewModel() {

    protected val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleException(throwable)
    }

    protected val ioContext = Dispatchers.IO + coroutineExceptionHandler

    protected abstract val _state: MutableStateFlow<STATE>
    abstract val state: StateFlow<STATE>

    protected abstract fun handleException(throwable: Throwable)

    protected abstract fun getSuccessData(state: STATE): UI?
    protected abstract fun createSuccessState(data: UI): STATE

    protected fun updateIfSuccess(block: (UI) -> UI) {
        val currentState = _state.value
        val currentData = getSuccessData(currentState)
        if (currentData != null) {
            _state.value = createSuccessState(block(currentData))
        }
    }

    protected fun <R> getIfSuccess(block: (UI) -> R): R? {
        val currentState = _state.value
        val currentData = getSuccessData(currentState)
        return currentData?.let(block)
    }

    protected fun doIfSuccess(block: (UI) -> Unit) {
        val currentState = _state.value
        val currentData = getSuccessData(currentState)
        currentData?.let(block)
    }

    protected suspend fun showSnackMessage(message: String, type: SnackbarMessageType) {

    }

    protected suspend fun updateAppState(
        topBarUiSettings: TopBarUiSettings,
        isBottomNavVisible: Boolean = true
    ) {
        sharedFlowMap.emit(
            UI_EVENT,
            UiEvent.UpdateAppState(
                topBarUiSettings = topBarUiSettings,
                isBottomNavVisible = isBottomNavVisible
            )
        )
    }
}