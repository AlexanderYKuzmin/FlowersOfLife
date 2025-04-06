package com.kuzmin.flowersoflife.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.kuzmin.flowersoflife.core.domain.model.UserRole

interface FeatureNavGraph {
    fun registerNavGraph(
        navController: NavController,
        navGraphBuilder: NavGraphBuilder
    )
}