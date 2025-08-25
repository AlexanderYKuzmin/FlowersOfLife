package com.kuzmin.flowersoflife.core.ui.components.snackbar

import com.kuzmin.flowersoflife.core.test_utils.MainDispatcherRule
import com.kuzmin.flowersoflife.core.ui.event.UiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SnackbarControllerImplTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var sharedFlow: MutableSharedFlow<UiEvent>
    private lateinit var controller: SnackbarControllerImpl

    @Before
    fun setup() {
        sharedFlow = MutableSharedFlow(replay = 1)
        controller = SnackbarControllerImpl(sharedFlow)
    }

    @Test
    fun `showMessage emits ShowSnackbar event`() = runTest {
        val collected = mutableListOf<UiEvent>()
        val job = launch {
            sharedFlow.collect { collected.add(it) }
        }

        controller.showMessage("Test Message", SnackbarMessageType.INFO)

        advanceUntilIdle()

        val event = collected.firstOrNull() as? UiEvent.ShowSnackbar
        assertNotNull(event)
        assertEquals("Test Message", event?.message)
        assertEquals(SnackbarMessageType.INFO, event?.type)

        job.cancel()
    }
}