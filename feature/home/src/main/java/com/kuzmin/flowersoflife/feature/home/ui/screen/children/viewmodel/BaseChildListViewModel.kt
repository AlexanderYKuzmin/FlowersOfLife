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

abstract class BaseChildrenListviewModel<UI, STATE>(
    private val sharedFlowMap: SharedFlowMap<UiEvent>
) : ViewModel() {

    protected val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleException(throwable)
    }

    protected val ioContext = Dispatchers.IO + coroutineExceptionHandler

    protected abstract val _state: MutableStateFlow<STATE>
    abstract val state: StateFlow<STATE>

    protected abstract fun handleException(throwable: Throwable)

    protected abstract fun getSuccessData(state: STATE): List<UI>?
    protected abstract fun createSuccessState(data: List<UI>): STATE

    protected fun updateIfSuccess(block: (List<UI>) -> List<UI>) {
        val currentState = _state.value
        val currentData = getSuccessData(currentState)
        if (currentData != null) {
            _state.value = createSuccessState(block(currentData))
        }
    }

    protected fun <R> getIfSuccess(block: (List<UI>) -> R): R? {
        val currentState = _state.value
        val currentData = getSuccessData(currentState)
        return currentData?.let(block)
    }

    protected fun doIfSuccess(block: (List<UI>) -> Unit) {
        val currentState = _state.value
        val currentData = getSuccessData(currentState)
        currentData?.let(block)
    }

    protected fun updateItem(predicate: (UI) -> Boolean, update: (UI) -> UI) {
        updateIfSuccess { list ->
            list.map { item ->
                if (predicate(item)) update(item) else item
            }
        }
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