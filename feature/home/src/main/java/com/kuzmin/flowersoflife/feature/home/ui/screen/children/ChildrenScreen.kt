package com.kuzmin.flowersoflife.feature.home.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kuzmin.flowersoflife.core.domain.model.aggregate.ChildDashboard
import com.kuzmin.flowersoflife.core.ui.R
import com.kuzmin.flowersoflife.core.ui.components.dialog.AlertDialogCard
import com.kuzmin.flowersoflife.feature.home.ui.component.ChildCard
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.state.BaseChildrenListState
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.viewmodel.ChildrenListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChildrenScreen(
    modifier: Modifier = Modifier,
    viewModel: ChildrenListViewModel = koinViewModel(),
) {
    val state = viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        when(val uiState = state.value) {
            is BaseChildrenListState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            is BaseChildrenListState.Success -> {
                ChildrenScreenContent(
                    children = uiState.children,
                    onChildClick = { childDashboard ->
                        viewModel.onChildClick(childDashboard)
                    }
                )
            }

            is BaseChildrenListState.Error -> {
                AlertDialogCard(
                    title = stringResource(R.string.error_title),
                    message = uiState.message,
                    confirmText = stringResource(R.string.ok_btn),
                    onConfirm = viewModel::onBackPressed,
                    onDismissRequest = viewModel::onBackPressed,
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.baseline_error_outline_24),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun ChildrenScreenContent(
    children: List<ChildDashboard>,
    onChildClick: (child: ChildDashboard) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        children.forEach { childDashboard ->
            ChildCard(
                child = childDashboard,
                onChildClick = { onChildClick(childDashboard) }
            )
        }
    }
}