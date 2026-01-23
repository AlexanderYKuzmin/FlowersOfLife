package com.kuzmin.flowersoflife.ui.components

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.UserRole
import com.kuzmin.flowersoflife.core.navigation.FeatureNavGraph
import com.kuzmin.flowersoflife.core.navigation.NavigationManager
import com.kuzmin.flowersoflife.core.navigation.model.NavigationCommand
import com.kuzmin.flowersoflife.core.navigation.routing.Destination
import com.kuzmin.flowersoflife.core.navigation.routing.Route

@Composable
fun AppNavHost(
    user: User?,
    navController: NavHostController,
    navigationManager: NavigationManager,
    featureNavGraphs: Set<@JvmSuppressWildcards FeatureNavGraph>,
    paddingValues: PaddingValues
) {

    LaunchedEffect(Unit) {
        navigationManager.commands.collect { command ->
            when (command) {
                is NavigationCommand.ToDestination -> {
                    Log.d("CAB-2-2", "ToDestination. buildDestination: ${command.buildDestination()}")

                    navController.navigate(command.buildDestination())
                }

                is NavigationCommand.ToDestinationParcelable -> {
                    command.parcelableArgs?.forEach { (key, value) ->
                        navController.currentBackStackEntry?.savedStateHandle?.set(key, value)
                    }

                    navController.navigate(command.destination)
                }

                is NavigationCommand.ToGraph -> {
                    navController.navigate(command.targetRoute) {
                        popUpTo(command.popUpTo) { inclusive = true }
                    }
                }

                is NavigationCommand.Back -> {
                    navController.popBackStack() //TODO Дописать варианты возврата до экрана и т.д.
                }
                else -> {}
            }
        }
    }

    val graph = when {
        user == null -> Route.AUTH_NAV_GRAPH
        user.role == UserRole.PARENT -> Route.PARENT_NAV_GRAPH
        user.role == UserRole.CHILD -> Route.CHILD_NAV_GRAPH
        else -> Route.AUTH_NAV_GRAPH
    }
    val startRoute = when(graph) {
        Route.AUTH_NAV_GRAPH -> Destination.AUTH_LOGIN
        Route.PARENT_NAV_GRAPH -> Destination.PARENT_CHILDREN_LIST
        Route.CHILD_NAV_GRAPH -> Destination.CHILD_HOME
        else -> Destination.AUTH_LOGIN
    }
    NavHost(
        navController = navController,
        startDestination = graph,
        route = startRoute,
        modifier = Modifier.padding(paddingValues)
    ) {

        featureNavGraphs.forEach { featureNavGraph ->
            featureNavGraph.registerNavGraph(navController, this)
        }
    }
}