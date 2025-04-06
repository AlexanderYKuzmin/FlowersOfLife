package com.kuzmin.flowersoflife.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kuzmin.flowersoflife.common.constants.Destination
import com.kuzmin.flowersoflife.common.constants.Route
import com.kuzmin.flowersoflife.core.navigation.FeatureNavGraph
import com.kuzmin.flowersoflife.feature.home.ui.screen.HomeScreen
import javax.inject.Inject

class ChildNavGraph @Inject constructor() : FeatureNavGraph {
    override fun registerNavGraph(
        navController: NavController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation(
            route = Route.CHILD_NAV_GRAPH,
            startDestination = Destination.CHILD_HOME
        ) {
            composable(route = Destination.CHILD_HOME) {
                HomeScreen()
            }
        }
    }
}