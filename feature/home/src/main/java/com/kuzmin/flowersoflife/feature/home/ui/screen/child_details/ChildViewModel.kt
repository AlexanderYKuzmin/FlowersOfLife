package com.kuzmin.flowersoflife.feature.home.ui.screen.child_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.kuzmin.flowersoflife.core.navigation.routing.DestinationArgs

class ChildViewModel(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    init {
        val childId: String = checkNotNull(savedStateHandle[DestinationArgs.CHILD_ID])
    }

    private fun fetchChild(childId: String) {

    }
}