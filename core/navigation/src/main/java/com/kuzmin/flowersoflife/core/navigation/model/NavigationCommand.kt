package com.kuzmin.flowersoflife.core.navigation.model

sealed class NavigationCommand {
    data class ToDestination(val destination: String) : NavigationCommand()
    data class ToDestinationWithArgs(val destination: String, val args: Map<String, Any>) : NavigationCommand()
    data class ToGraph(val targetRoute: String, val popUpTo: String, val inclusive: Boolean = false) : NavigationCommand()
    data class Back(val route: String? = null, val inclusive: Boolean = false) : NavigationCommand()
    data class Replace(val destination: String, val popUpTo: String) : NavigationCommand()
}

