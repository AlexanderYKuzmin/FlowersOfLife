package com.kuzmin.flowersoflife.feature.home.ui.screen.children.state

sealed interface ChildrenListState {
    data object Loading: ChildrenListState
    data class Success(
       // val children: List<Child>
    )
}