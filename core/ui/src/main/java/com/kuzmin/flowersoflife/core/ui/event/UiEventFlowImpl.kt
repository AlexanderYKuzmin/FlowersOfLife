package com.kuzmin.flowersoflife.core.ui.event

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class UiEventFlowImpl @Inject constructor(
) : UiEventFlow {

    private val _events = MutableSharedFlow<UiEvent>()
    override val events: SharedFlow<UiEvent> = _events.asSharedFlow()

    override suspend fun emit(event: UiEvent) {
        _events.emit(event)
    }
}
