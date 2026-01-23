package com.kuzmin.flowersoflife.feature.home.ui.screen.children.state

sealed interface BaseChildrenListState<out T> {
    data class Success<T>(val children: List<T>) : BaseChildrenListState<T>
    data class Error(val message: String) : BaseChildrenListState<Nothing>
    object Loading : BaseChildrenListState<Nothing>
}