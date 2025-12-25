package com.kuzmin.flowersoflife.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface FeatureNavGraph {
    fun registerNavGraph(
        navController: NavController,
        navGraphBuilder: NavGraphBuilder
    )
}