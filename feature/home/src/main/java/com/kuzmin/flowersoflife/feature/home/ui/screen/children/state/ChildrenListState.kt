package com.kuzmin.flowersoflife.feature.home.ui.screen.children.state

import com.kuzmin.flowersoflife.core.domain.model.family_members.ChildDetails

sealed interface ChildrenListState {

    data object Loading : ChildrenListState

    data class Success(
       val children: List<ChildDetails>
    ) : ChildrenListState

    data class Error(val message: String) : ChildrenListState
}