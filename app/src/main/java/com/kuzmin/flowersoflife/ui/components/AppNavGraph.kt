package com.kuzmin.flowersoflife.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kuzmin.flowersoflife.core.navigation.FeatureNavGraph

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String,
    featureNavGraphs: Set<@JvmSuppressWildcards FeatureNavGraph>,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.padding(paddingValues)
    ) {

        featureNavGraphs.forEach { featureNavGraph ->
            featureNavGraph.registerNavGraph(navController, this)
        }
    }
}