package com.kuzmin.flowersoflife.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.kuzmin.flowersoflife.core.navigation.FeatureNavGraph
import com.kuzmin.flowersoflife.core.navigation.NavigationManager
import com.kuzmin.flowersoflife.core.ui.components.snackbarhost.CustomSnackbarHost
import com.kuzmin.flowersoflife.core.ui.extensions.showTypedSnackbar
import com.kuzmin.flowersoflife.core.ui.theme.KabTheme
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

    val navigationManager = viewModel.getNavigationManager()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.notifyTopbarStateChange()
        viewModel.snackbarState.collect { snackbarData ->
            snackbarHostState.showTypedSnackbar(snackbarData)
        }
    }

    when(val appUiState = appState) {
        is AppUiState.Success -> {
            MainScreenContent(
                appState = appUiState,
                snackbarHostState = snackbarHostState,
                navController = navController,
                navigationManager = navigationManager,
                featureNavGraph = featureNavGraph,
            )
        }
        is AppUiState.Error -> {
            // TODO: show error screen
        }
        else -> {}
    }

}

@Composable
fun MainScreenContent(
    appState: AppUiState.Success,
    snackbarHostState: SnackbarHostState,
    navController: NavHostController,
    navigationManager: NavigationManager,
    featureNavGraph: Set<@JvmSuppressWildcards FeatureNavGraph>,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                modifier = Modifier.fillMaxWidth(0.6f),
                userFamily = appState.userFamily,
            )
        }
    ) {

        Scaffold(
            snackbarHost = { CustomSnackbarHost(snackbarHostState) },
            topBar = {
                MainScreenTopBar(
                    tabbarUiSettings = appState.topBarUiSettings,
                    onNavigationIconClick = {
                        scope.launch { drawerState.open() }
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            },
            bottomBar = {
                if (appState.isAuthorized) {
                    ParentBottomNavigationBar(
                        navController = navController
                    )
                }
            }
        ) { innerPadding ->
            AppNavHost(
                user = appState.userFamily?.user,
                navController = navController,
                navigationManager = navigationManager,
                featureNavGraphs = featureNavGraph,
                paddingValues = innerPadding
            )
        }
    }
}
