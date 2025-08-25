package com.kuzmin.flowersoflife.core.navigation.model

sealed class NavigationCommand {
    data class ToDestination(val destination: String) : NavigationCommand()

    data class ToGraph(val targetRoute: String, val popUpTo: String) : NavigationCommand()

    data object Back : NavigationCommand()
}

