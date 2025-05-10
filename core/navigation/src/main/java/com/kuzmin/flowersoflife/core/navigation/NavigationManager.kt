package com.kuzmin.flowersoflife.core.navigation

import androidx.navigation.NavOptionsBuilder

interface NavigationManager {
    fun navigate(destination: String)
    fun navigate(route: String, builder: NavOptionsBuilder.() -> Unit)
    fun popBackStack(): Boolean
    fun popUpTo(destination: String)
    fun navigateUp(): Boolean
}