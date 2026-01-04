package com.kuzmin.flowersoflife.feature.home.ui.screen.child_details.state

import com.kuzmin.flowersoflife.core.domain.model.aggregate.ChildDashboard

sealed interface ChildDetailsState {

    data object Loading : ChildDetailsState

    data class Success(
        val childDashboard: ChildDashboard
    ) : ChildDetailsState

    data class Error(val message: String) : ChildDetailsState
}