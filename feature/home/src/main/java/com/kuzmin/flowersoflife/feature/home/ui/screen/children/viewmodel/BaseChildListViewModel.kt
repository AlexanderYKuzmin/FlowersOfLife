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
import kotlinx.coroutines.flow.update

abstract class BaseChildrenListviewModel<ITEM, STATE>(
    private val sharedFlowMap: SharedFlowMap<UiEvent>
) : ViewModel() {

    protected val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleException(throwable)
    }

    protected val ioContext = Dispatchers.IO + coroutineExceptionHandler

    protected abstract val _state: MutableStateFlow<STATE>
    abstract val state: StateFlow<STATE>

    protected abstract fun handleException(throwable: Throwable)

    protected inline fun <reified SUCCESS_STATE: STATE> updateIfSuccess(
        crossinline block: (SUCCESS_STATE) -> SUCCESS_STATE
    ) {
        _state.update { currentState ->
            (currentState as? SUCCESS_STATE)?.let { block(it) } ?: currentState
        }
    }

    protected inline fun <reified SUCCESS_STATE: STATE> doIfSuccess(
        crossinline block: (SUCCESS_STATE) -> Unit
    ) {
        _state.value?.let {
            if (it is SUCCESS_STATE) block(it)
        }
    }

    protected inline fun <reified SUCCESS_STATE: STATE, R> getIfSuccess(
        crossinline block: (SUCCESS_STATE) -> R
    ): R? {
        return (_state.value as? SUCCESS_STATE)?.let(block)
    }

    protected abstract fun getChildrenList(state: STATE): List<ITEM>?
    protected abstract fun updateChildrenList(state: STATE, newList: List<ITEM>): STATE

    protected fun updateItem(
        predicate: (ITEM) -> Boolean,
        update: (ITEM) -> ITEM
    ) {
        _state.update { currentState ->
            val currentList = getChildrenList(currentState)
            if (currentList != null) {
                val updatedList = currentList.map { item ->
                    if (predicate(item)) update(item) else item
                }
                updateChildrenList(currentState, updatedList)
            } else {
                currentState
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