package com.kuzmin.flowersoflife.navigation.graph

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.UserRole
import com.kuzmin.flowersoflife.core.navigation.routing.Destination
import com.kuzmin.flowersoflife.core.navigation.routing.Route

@Composable
fun RootFlowScreen(
    user: User,
    navController: NavController
) {
    val route = when {
        !user.isAuthorized -> Route.AUTH_NAV_GRAPH
        user.role == UserRole.PARENT -> Route.PARENT_NAV_GRAPH
        user.role == UserRole.CHILD -> Route.CHILD_NAV_GRAPH
        else -> Route.AUTH_NAV_GRAPH
    }

    navController.navigate(route) {
        popUpTo(Destination.ROOT) { inclusive = true }
        launchSingleTop = true
    }

    Log.d("CAB-9", "✅ Navigation from RootFlowScreen to $route")
}
