package com.kuzmin.flowersoflife.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kuzmin.flowersoflife.core.domain.model.family_members.Child
import com.kuzmin.flowersoflife.core.navigation.FeatureNavGraph
import com.kuzmin.flowersoflife.core.navigation.routing.Destination
import com.kuzmin.flowersoflife.core.navigation.routing.DestinationArgs
import com.kuzmin.flowersoflife.core.navigation.routing.Route
import com.kuzmin.flowersoflife.feature.home.ui.screen.ChildrenScreen
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.ChildEditScreen

class ParentNavGraph : FeatureNavGraph {
    override fun registerNavGraph(
        navController: NavController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation(
            route = Route.PARENT_NAV_GRAPH,
            startDestination = Destination.PARENT_CHILDREN_LIST
        ) {
            composable(
                route = Destination.PARENT_CHILDREN_LIST
            ) {
                ChildrenScreen()
            }

            composable(
                route = Destination.PARENT_EDIT_CHILD,
                arguments = listOf(
                    navArgument(DestinationArgs.CHILD) {
                        type = NavType.ParcelableType(
                            type = Child::class.java
                        )
                        nullable = true
                    }
                )
            ) {
                ChildEditScreen()
            }
        }
    }
}