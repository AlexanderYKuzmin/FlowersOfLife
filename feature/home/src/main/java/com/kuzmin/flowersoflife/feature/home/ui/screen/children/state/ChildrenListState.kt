package com.kuzmin.flowersoflife.feature.home.ui.screen.children.state

import com.kuzmin.flowersoflife.core.domain.model.family_members.Child

sealed interface ChildrenListState {

    data object Loading : ChildrenListState

    data class Success(
       val children: List<Child>
    ) : ChildrenListState

    data class Error(val message: String) : ChildrenListState
}