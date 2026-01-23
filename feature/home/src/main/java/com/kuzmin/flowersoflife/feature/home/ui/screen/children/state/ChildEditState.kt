package com.kuzmin.flowersoflife.feature.home.ui.screen.children.state

import com.kuzmin.flowersoflife.feature.home.ui.model.ChildUi

sealed interface ChildEditState {

    data object Loading : ChildEditState

    data class Success(
        val child: ChildUi
    ) : ChildEditState

    data class Error(val message: String) : ChildEditState
}