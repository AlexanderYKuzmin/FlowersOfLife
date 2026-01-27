package com.kuzmin.flowersoflife.feature.home.ui.screen.children

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.kuzmin.flowersoflife.common.R
import com.kuzmin.flowersoflife.core.domain.model.aggregate.ChildDashboard
import com.kuzmin.flowersoflife.feature.home.ui.component.ChildDashboardCard
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.state.BaseChildrenListState
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.viewmodel.HomeChildrenDashboardViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeChildrenDashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeChildrenDashboardViewModel = koinViewModel(),
) {

    val state by viewModel.state.collectAsState()

    when (val uiState = state) {
        is BaseChildrenListState.Loading -> {

        }
        is BaseChildrenListState.Success -> {
            HomeChildrenDashboardScreenContent(
                modifier = modifier,
                state = uiState,
                onChildClick = {
                    Log.d("CAB-2-3", "Child clicked: $it")
                }
            )
        }
        is BaseChildrenListState.Error -> {

        }
    }
}

@Composable
fun HomeChildrenDashboardScreenContent(
    modifier: Modifier = Modifier,
    state: BaseChildrenListState.Success<ChildDashboard>,
    onChildClick: (String) -> Unit,
) {
    val scrollState = rememberScrollState()
    val children = state.children

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.castle_pic),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            children.forEach { childDashboard ->
                ChildDashboardCard(
                    childDashboard = childDashboard,
                    onChildClick = onChildClick
                )
            }
        }
    }
}