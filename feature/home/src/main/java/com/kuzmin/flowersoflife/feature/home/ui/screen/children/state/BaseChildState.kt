package com.kuzmin.flowersoflife.feature.home.ui.screen.children.state

sealed interface BaseChildState<out T> {
    data class Success<T>(val data: T) : BaseChildState<T>
    data class Error(val error: String) : BaseChildState<Nothing>
    object Loading : BaseChildState<Nothing>
}