package com.kuzmin.flowersoflife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.kuzmin.flowersoflife.core.navigation.FeatureNavGraph
import com.kuzmin.flowersoflife.core.ui.theme.KabTheme
import com.kuzmin.flowersoflife.ui.screen.MainScreen
import com.kuzmin.flowersoflife.ui.screen.SplashScreenAnimated
import com.kuzmin.flowersoflife.ui.state.MainScreenState
import com.kuzmin.flowersoflife.ui.viewmodels.MainScreenViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val featureNavGraph: Set<FeatureNavGraph> by inject()
    private val viewModel: MainScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            KabTheme {
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