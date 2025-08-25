package com.kuzmin.flowersoflife.navigation.graph

import android.util.Log
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
        Log.d("CAB-9", "🔧 ParentNavGraph registered")
        navGraphBuilder.navigation(
            route = Route.PARENT_NAV_GRAPH,
            startDestination = Destination.PARENT_HOME
        ) {
            Log.d("CAB-9", "📍 registering parent_home destination")
            composable(route = Destination.PARENT_HOME) {
                Log.d("CAB-9", "✅ parent_home hit!")
                HomeScreen()
            }
        }
    }
}