package com.kuzmin.flowersoflife.feature.home.ui.screen.children.state

import com.kuzmin.flowersoflife.core.domain.model.aggregate.ChildDashboard

sealed interface ChildrenListState {

    data object Loading : ChildrenListState

    data class Success(
       val children: List<ChildDashboard>
    ) : ChildrenListState

    data class Error(val message: String) : ChildrenListState
}