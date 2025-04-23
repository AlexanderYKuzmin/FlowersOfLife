package com.kuzmin.flowersoflife.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kuzmin.flowersoflife.common.constants.Route
import com.kuzmin.flowersoflife.common.constants.Destination
import com.kuzmin.flowersoflife.core.navigation.FeatureNavGraph
import com.kuzmin.flowersoflife.feature.auth.ui.screen.AuthLoginScreen
import com.kuzmin.flowersoflife.feature.auth.ui.screen.AuthRegisterScreen
import com.kuzmin.flowersoflife.feature.auth.ui.screen.AuthResetScreen
import javax.inject.Inject

class AuthNavGraph @Inject constructor() : FeatureNavGraph {
    override fun registerNavGraph(
        navController: NavController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation(
            route = Route.AUTH_NAV_GRAPH,
            startDestination = Destination.AUTH_LOGIN
        ) {
            composable(route = Destination.AUTH_LOGIN) {
                AuthLoginScreen()
            }

            composable(route = Destination.AUTH_REGISTER) {
                AuthRegisterScreen()
            }

            composable(route = Destination.AUTH_RESET) {
                AuthResetScreen(
                    onNavigateToLogin = {
                        navController.navigate(Destination.AUTH_LOGIN)
                    }
                )
            }
        }
    }
}