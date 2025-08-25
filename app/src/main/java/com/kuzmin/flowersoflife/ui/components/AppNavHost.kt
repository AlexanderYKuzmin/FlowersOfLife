package com.kuzmin.flowersoflife.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kuzmin.flowersoflife.common.constants.Destination
import com.kuzmin.flowersoflife.common.constants.Route
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.UserRole
import com.kuzmin.flowersoflife.core.navigation.FeatureNavGraph
import com.kuzmin.flowersoflife.core.navigation.model.NavigationCommand
import com.kuzmin.flowersoflife.ui.state.AppUiState
import com.kuzmin.flowersoflife.ui.viewmodels.MainScreenViewModel

@Composable
fun AppNavHost(
    viewModel: MainScreenViewModel,
    navController: NavHostController,
    featureNavGraphs: Set<@JvmSuppressWildcards FeatureNavGraph>,
    paddingValues: PaddingValues
) {
    val appState by viewModel.appState.collectAsState()

    val navigationManager = viewModel.getNavigationManager()

    var hasEntered by remember { mutableStateOf(false) }

    LaunchedEffect(appState) {
        if (!hasEntered && appState is AppUiState.Success) {
            hasEntered = true
        }
    }

    LaunchedEffect(Unit) {
        navigationManager.commands.collect { command ->
            when (command) {
                is NavigationCommand.ToDestination -> {
                    navController.navigate(command.destination)
                }

                is NavigationCommand.ToGraph -> {
                    navController.navigate(command.targetRoute) {
                        popUpTo(command.popUpTo) { inclusive = true }
                    }
                }

                NavigationCommand.Back -> {
                    navController.popBackStack()
                }
            }
        }
    }


    if (hasEntered) {
        val user = (appState as? AppUiState.Success)?.user ?: User()
        val graph = when {
            !user.isAuthorized -> Route.AUTH_NAV_GRAPH
            user.role == UserRole.PARENT -> Route.PARENT_NAV_GRAPH
            user.role == UserRole.CHILD -> Route.CHILD_NAV_GRAPH
            else -> Route.AUTH_NAV_GRAPH
        }
        val startRoute = when(graph) {
            Route.AUTH_NAV_GRAPH -> Destination.AUTH_LOGIN
            Route.PARENT_NAV_GRAPH -> Destination.PARENT_HOME
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

}