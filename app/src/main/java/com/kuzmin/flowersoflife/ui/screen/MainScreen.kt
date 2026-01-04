package com.kuzmin.flowersoflife.ui.screen

import androidx.activity.compose.LocalActivity
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.kuzmin.flowersoflife.common.ext.setSystemBarsAppearance
import com.kuzmin.flowersoflife.common.model.TabBarUiSettings
import com.kuzmin.flowersoflife.core.navigation.FeatureNavGraph
import com.kuzmin.flowersoflife.core.ui.components.snackbarhost.CustomSnackbarHost
import com.kuzmin.flowersoflife.core.ui.extensions.showTypedSnackbar
import com.kuzmin.flowersoflife.ui.components.AppNavHost
import com.kuzmin.flowersoflife.ui.components.DrawerContent
import com.kuzmin.flowersoflife.ui.components.MainScreenTopBar
import com.kuzmin.flowersoflife.ui.components.ParentBottomNavigationBar
import com.kuzmin.flowersoflife.ui.state.AppUiState
import com.kuzmin.flowersoflife.ui.viewmodels.MainScreenViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    navController: NavHostController,
    featureNavGraph: Set<@JvmSuppressWildcards FeatureNavGraph>,
    viewModel: MainScreenViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {

    val appState by viewModel.appState.collectAsState()

    val activity = LocalActivity.current
    val statusBarColor = MaterialTheme.colorScheme.surface
    val navigationBarColor = MaterialTheme.colorScheme.surface

    val snackbarHostState = remember { SnackbarHostState() }

    SideEffect {
        activity?.setSystemBarsAppearance(
            statusBarColor = statusBarColor,
            navigationBarColor = navigationBarColor
        )
    }

    LaunchedEffect(Unit) {
        viewModel.snackbarState.collect { snackbarData ->
            snackbarHostState.showTypedSnackbar(snackbarData)
        }
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val currentState = appState as? AppUiState.Success
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent()
        }
    ) {

        Scaffold(
            snackbarHost = { CustomSnackbarHost(snackbarHostState) },
            topBar = {
                MainScreenTopBar(
                    tabbarUiSettings = currentState?.tabbarUiSettings ?: TabBarUiSettings(),
                    onNavigationIconClick = {
                        scope.launch { drawerState.open() }
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            },
            bottomBar = {
                when(appState) {
                    is AppUiState.Success -> {
                        if ((appState as AppUiState.Success).isAuthorized) {
                            ParentBottomNavigationBar(
                                navController = navController
                            )
                        }
                    }
                    else -> {}
                }
            }
        ) { innerPadding ->
            AppNavHost(
                viewModel = viewModel,
                navController = navController,
                featureNavGraphs = featureNavGraph,
                paddingValues = innerPadding
            )
        }
    }
}