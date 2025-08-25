package com.kuzmin.flowersoflife.ui.state

sealed interface MainScreenState {

    data object Loading : MainScreenState

    data class Error(val message: String) : MainScreenState

    data object SuccessEmpty : MainScreenState
}