package com.kuzmin.flowersoflife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.kuzmin.flowersoflife.core.navigation.FeatureNavGraph
import com.kuzmin.flowersoflife.core.ui.event.UiEventFlow
import com.kuzmin.flowersoflife.core.ui.theme.FlowersOfLifeTheme
import com.kuzmin.flowersoflife.ui.screen.MainScreen
import com.kuzmin.flowersoflife.ui.screen.SplashScreenAnimated
import com.kuzmin.flowersoflife.ui.state.MainScreenState
import com.kuzmin.flowersoflife.ui.viewmodels.MainScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var featureNavGraph: Set<@JvmSuppressWildcards FeatureNavGraph>

    @Inject
    lateinit var uiEventFlow: UiEventFlow

    private val viewModel: MainScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            FlowersOfLifeTheme {
                val mainScreenState by viewModel.screenState.collectAsState()

                val navController = rememberNavController()

                when (mainScreenState) {
                    is MainScreenState.Loading -> SplashScreenAnimated()
                    is MainScreenState.SuccessEmpty -> MainScreen(
                        navController = navController,
                        viewModel = viewModel,
                        featureNavGraph = featureNavGraph,
                    )

                    else -> Unit
                }
            }
        }
    }
}