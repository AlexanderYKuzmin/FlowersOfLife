package com.kuzmin.flowersoflife.navigation.manager

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.kuzmin.flowersoflife.core.navigation.NavigationManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationManagerImpl @Inject constructor() : NavigationManager {

    private var navController: NavController? = null

    fun setNavController(controller: NavController) {
        navController = controller
    }

    override fun navigate(destination: String) {
        navController?.navigate(destination)
    }

    override fun navigate(route: String, builder: NavOptionsBuilder.() -> Unit) {
        navController?.navigate(route, builder)
    }

    override fun popBackStack(): Boolean {
        return navController?.popBackStack() ?: false
    }

    override fun navigateUp(): Boolean {
        return navController?.navigateUp() ?: false
    }
}