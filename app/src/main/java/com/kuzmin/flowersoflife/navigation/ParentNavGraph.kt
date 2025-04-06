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

class ParentNavGraph @Inject constructor() : FeatureNavGraph {
    override fun registerNavGraph(
        navController: NavController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation(
            route = Route.PARENT_NAV_GRAPH,
            startDestination = Destination.PARENT_HOME
        ) {
            composable(route = Destination.PARENT_HOME) {
                HomeScreen()
            }
        }
    }
}