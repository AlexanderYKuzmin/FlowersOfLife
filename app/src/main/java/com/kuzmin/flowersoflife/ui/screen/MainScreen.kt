package com.kuzmin.flowersoflife.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.navigation.compose.rememberNavController
import com.kuzmin.flowersoflife.common.constants.Route
import com.kuzmin.flowersoflife.core.domain.model.UserRole
import com.kuzmin.flowersoflife.core.navigation.FeatureNavGraph
import com.kuzmin.flowersoflife.domain.model.AppUiState
import com.kuzmin.flowersoflife.navigation.manager.NavigationManagerImpl
import com.kuzmin.flowersoflife.ui.components.AppNavGraph
import com.kuzmin.flowersoflife.ui.components.ParentBottomNavigationBar
import com.kuzmin.flowersoflife.ui.components.DrawerContent
import com.kuzmin.flowersoflife.ui.components.MainScreenTopBar
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    featureNavGraph: Set<@JvmSuppressWildcards FeatureNavGraph>,
    appState: AppUiState,
    snackbarHostState: SnackbarHostState,
    navigationManagerImpl: NavigationManagerImpl,
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val userRole = (appState as? AppUiState.Success)?.user?.role
    val isAuthorized = (appState as? AppUiState.Success)?.isAuthorized ?: false
    val startDestination = if (isAuthorized) {
        when(userRole) {
            UserRole.PARENT -> Route.PARENT_NAV_GRAPH
            UserRole.CHILD -> Route.CHILD_NAV_GRAPH
            else -> Route.AUTH_NAV_GRAPH
        }
    } else Route.AUTH_NAV_GRAPH

    LaunchedEffect(Unit) {
        navigationManagerImpl.setNavController(navController)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent()
        }
    ) {

        Scaffold(
            topBar = {
                MainScreenTopBar(
                    onNavigationIconClick = {
                        scope.launch { drawerState.open() }
                    }
                )
            },
            bottomBar = {
                if (isAuthorized) {
                    ParentBottomNavigationBar(
                        navController = navController
                    )
                }
            }
        ) { innerPadding ->

            AppNavGraph(
                navController = navController,
                startDestination = startDestination,
                featureNavGraphs = featureNavGraph,
                paddingValues = innerPadding
            )

        }
    }
}