package com.kuzmin.flowersoflife.ui.screen

import android.app.Activity
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.kuzmin.flowersoflife.common.constants.Route
import com.kuzmin.flowersoflife.common.ext.setSystemBarsAppearance
import com.kuzmin.flowersoflife.core.domain.model.UserRole
import com.kuzmin.flowersoflife.core.navigation.FeatureNavGraph
import com.kuzmin.flowersoflife.core.ui.components.snackbarhost.CustomSnackbarHost
import com.kuzmin.flowersoflife.ui.state.AppUiState
import com.kuzmin.flowersoflife.navigation.manager.NavigationManagerImpl
import com.kuzmin.flowersoflife.ui.components.AppNavGraph
import com.kuzmin.flowersoflife.ui.components.DrawerContent
import com.kuzmin.flowersoflife.ui.components.MainScreenTopBar
import com.kuzmin.flowersoflife.ui.components.ParentBottomNavigationBar
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    featureNavGraph: Set<@JvmSuppressWildcards FeatureNavGraph>,
    appState: AppUiState.Success,
    snackBarHostState: SnackbarHostState,
    navigationManagerImpl: NavigationManagerImpl,
    modifier: Modifier = Modifier
) {
    val activity = LocalContext.current as Activity
    val statusBarColor = MaterialTheme.colorScheme.surface
    val navigationBarColor = MaterialTheme.colorScheme.surface

    SideEffect {
        activity.setSystemBarsAppearance(
            statusBarColor = statusBarColor,
            navigationBarColor = navigationBarColor
        )
    }

    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val userRole = appState.user?.role
    val isAuthorized = appState.isAuthorized

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
            snackbarHost = { CustomSnackbarHost(snackBarHostState) },
            topBar = {
                MainScreenTopBar(
                    title = appState.title,
                    isBackButtonVisible = appState.showBackButton,
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