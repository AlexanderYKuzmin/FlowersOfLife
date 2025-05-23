package com.kuzmin.flowersoflife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import com.kuzmin.flowersoflife.core.navigation.FeatureNavGraph
import com.kuzmin.flowersoflife.domain.model.AppUiState
import com.kuzmin.flowersoflife.domain.model.UiEvent
import com.kuzmin.flowersoflife.ui.screen.MainScreen
import com.kuzmin.flowersoflife.ui.screen.SplashScreenAnimated
import com.kuzmin.flowersoflife.common.ui.theme.FlowersOfLifeTheme
import com.kuzmin.flowersoflife.navigation.manager.NavigationManagerImpl
import com.kuzmin.flowersoflife.ui.viewmodels.MainScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var featureNavGraph: Set<@JvmSuppressWildcards FeatureNavGraph>

    @Inject
    lateinit var navigationManagerImpl: NavigationManagerImpl

    private val viewModel: MainScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            FlowersOfLifeTheme {
                val appState by viewModel.appState.collectAsState()

                val snackbarHostState = remember { SnackbarHostState() }
                val uiEvent = viewModel.uiEvent

                when (appState) {
                    is AppUiState.Loading -> SplashScreenAnimated()
                    is AppUiState.Success -> MainScreen(
                        appState = appState as AppUiState.Success,
                        featureNavGraph = featureNavGraph,
                        snackBarHostState = snackbarHostState,
                        navigationManagerImpl = navigationManagerImpl,
                    )

                    else -> Unit
                }


                LaunchedEffect(Unit) {
                    uiEvent.collect { event ->
                        when (event) {
                            is UiEvent.ShowSnackbar -> {
                                snackbarHostState.showSnackbar(event.message)
                            }

                            else -> Unit
                        }
                    }
                }
            }
        }
    }
}