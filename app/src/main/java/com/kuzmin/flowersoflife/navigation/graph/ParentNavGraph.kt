package com.kuzmin.flowersoflife.navigation.graph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kuzmin.flowersoflife.common.constants.Destination
import com.kuzmin.flowersoflife.common.constants.DestinationArgs
import com.kuzmin.flowersoflife.common.constants.Route
import com.kuzmin.flowersoflife.core.navigation.FeatureNavGraph
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.ChildrenScreen
import com.kuzmin.flowersoflife.feature.home.ui.screen.child_details.ChildViewModel
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.ChildrenListViewModel
import javax.inject.Inject

class ParentNavGraph @Inject constructor() : FeatureNavGraph {
    override fun registerNavGraph(
        navController: NavController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation(
            route = Route.PARENT_NAV_GRAPH,
            startDestination = Destination.PARENT_CHILDREN_LIST
        ) {
            composable(route = Destination.PARENT_CHILDREN_LIST) {
                val viewModel: ChildrenListViewModel = hiltViewModel()
                val state =
                ChildrenScreen(

                )
            }

            composable(
                route = Destination.PARENT_CHILD_DETAILS,
                arguments = listOf(navArgument(DestinationArgs.CHILD_ID) { type = NavType.StringType })
            ) {
                val viewModel: ChildViewModel = hiltViewModel()

                ParentDetailsScreen()
            }
        }
    }
}