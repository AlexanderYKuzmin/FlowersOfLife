package com.kuzmin.flowersoflife.core.ui.components.swipe

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue.EndToStart
import androidx.compose.material3.SwipeToDismissBoxValue.Settled
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kuzmin.flowersoflife.core.ui.theme.KabTheme
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.filter

@OptIn(FlowPreview::class)
@Composable
fun SwipeableListItem(
    item: Any,
    onRemove: (Any) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    var hasReachedThreshold by remember { mutableStateOf(false) }
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (hasReachedThreshold) {
                Log.d("Cab-2-1", "SwipeableListItem. Confirmvaluechange : $hasReachedThreshold")
                onRemove(item)
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(Unit) {
        Log.d("Cab-2-1", "SwipeableListItem. LaunchedEffect : ${swipeToDismissBoxState.progress}")
        snapshotFlow {
            Log.d("Cab-2-1", "SwipeableListItem. SnapshotFlow : ${swipeToDismissBoxState.progress}")
            swipeToDismissBoxState.progress
        }
            .filter {
                Log.d("Cab-2-1", "SwipeableListItem. Filter : $it")
                it > 0.4f && it < 1f
            }
            .collect {
                Log.d("Cab-2-1", "SwipeableListItem. Collect : $it")
                hasReachedThreshold = true
            }
    }

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        modifier = modifier,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            when (swipeToDismissBoxState.dismissDirection) {
                EndToStart -> {
                    Box(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxSize()
                            .background(
                                KabTheme.colors.frameInactive.copy(
                                    alpha = 0.5f
                                )
                            )
                            .wrapContentSize(Alignment.CenterEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Remove item",
                            modifier = Modifier
                                .padding(vertical = 12.dp, horizontal = 24.dp),
                            tint = KabTheme.colors.primaryText
                        )
                    }
                }

                Settled -> {}
                else -> Unit
            }
        },
        content = {
            content()
        }
    )


}