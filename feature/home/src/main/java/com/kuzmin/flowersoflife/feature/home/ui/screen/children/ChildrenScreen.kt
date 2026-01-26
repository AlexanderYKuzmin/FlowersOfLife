package com.kuzmin.flowersoflife.feature.home.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuzmin.flowersoflife.common.R
import com.kuzmin.flowersoflife.core.domain.model.aggregate.ChildDashboard
import com.kuzmin.flowersoflife.core.ui.components.dialog.AlertDialogCard
import com.kuzmin.flowersoflife.core.ui.components.swipe.SwipeableListItem
import com.kuzmin.flowersoflife.core.ui.theme.KabTheme
import com.kuzmin.flowersoflife.feature.home.ui.component.ChildCard
import com.kuzmin.flowersoflife.feature.home.ui.mock.mockChildDashboard
import com.kuzmin.flowersoflife.feature.home.ui.mock.mockUser
import com.kuzmin.flowersoflife.feature.home.ui.mock.mockWallet
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.state.BaseChildrenListState
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.viewmodel.ChildrenListViewModel
import org.koin.androidx.compose.koinViewModel
import com.kuzmin.flowersoflife.core.ui.R as CoreUiRes

@Composable
fun ChildrenScreen(
    modifier: Modifier = Modifier,
    viewModel: ChildrenListViewModel = koinViewModel(),
) {
    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) { viewModel.initAppState() }

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
                    },
                    onRemove = { childDashboard ->
                        viewModel.onRemoveChild(childDashboard)
                    }
                )

                if (uiState.pendingRemovalChild != null) {
                    AlertDialogCard(
                        title = stringResource(R.string.warning_title),
                        message = stringResource(R.string.remove_child_message),
                        confirmText = stringResource(R.string.ok_btn),
                        dismissText = stringResource(R.string.cancel_btn),
                        onConfirm = { viewModel.onRemoveChildApproved(uiState.pendingRemovalChild) },
                        onDismissRequest = { viewModel.refresh() },
                        onDismiss = { viewModel.refresh() },
                        icon = {
                            Icon(
                                painter = painterResource(CoreUiRes.drawable.baseline_warning_amber_24),
                                contentDescription = null,
                                tint = KabTheme.colors.warningText,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    )
                }
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
                            painter = painterResource(CoreUiRes.drawable.baseline_error_outline_24),
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
    onChildClick: (child: ChildDashboard?) -> Unit,
    onRemove: (child: ChildDashboard) -> Unit
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
            painter = painterResource(R.drawable.castle_children_line),
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            children.forEach { childDashboard ->
                SwipeableListItem(
                    item = childDashboard,
                    onRemove = { onRemove(childDashboard) }
                ) {
                    ChildCard(
                        child = childDashboard,
                        onChildClick = { onChildClick(childDashboard) }
                    )
                }
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .padding(16.dp)
                .size(56.dp)
                .align(Alignment.BottomEnd),
            containerColor = KabTheme.colors.redPaleTag,
            shape = CircleShape,
            onClick = { onChildClick(null) }
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(
                    CoreUiRes.drawable.outline_add_24
                ),
                contentDescription = null,
                contentScale = ContentScale.Inside
            )
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun ChildrenScreenPreview() {
    KabTheme {
        ChildrenScreenContent(
            children = listOf(
                mockChildDashboard,
                mockChildDashboard.copy(
                    user = mockUser.copy(
                        userId = "2",
                        name = "Alex"
                    ),
                    wallet = mockWallet.copy(
                        walletId = "2",
                        balance = 1000
                    )
                )
            ),
            onChildClick = {},
            onRemove = {}
        )
    }
}