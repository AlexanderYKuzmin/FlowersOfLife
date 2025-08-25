package com.kuzmin.flowersoflife.core.ui.event

import kotlinx.coroutines.flow.SharedFlow

interface UiEventFlow {
    val events: SharedFlow<UiEvent>
    suspend fun emit(event: UiEvent)
}
